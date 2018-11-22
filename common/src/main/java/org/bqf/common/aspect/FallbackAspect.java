package org.bqf.common.aspect;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.bqf.common.client.fallback.BaseServiceFallback;
import org.bqf.common.dto.BaseRsp;
import org.bqf.common.exception.BizException;
import org.bqf.common.fallback.FallbackConfig;
import org.bqf.common.fallback.FallbackConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.hystrix.FallbackFactory;

@Aspect
@Component
public class FallbackAspect {

    private static final Logger LOG = LoggerFactory.getLogger(FallbackAspect.class);

    @Value("${spring.application.name}")
    private String serviceName;
    
    /**
     * 定义一个切点.
     */
    @Pointcut("execution(public * org.bqf.test.controller.*.*(..))")
    public void controller() {
    }

    /**
     * 定义一个切点.
     */
    @Pointcut("execution(public * org.bqf.test.client.*.*(..))")
    public void client() {
    }

    /**
     * 方法被调用前执行.
     *
     * @param jp a join point
     * @throws Throwable 异常
     */
    @Around("controller()")
    public Object aroundController(final ProceedingJoinPoint jp) throws Throwable{
        Object[] args = jp.getArgs();
        String method = jp.getSignature().getName();
        String reqInfo;
        if (null == args || args.length < 1 || null == args[0]) {
            reqInfo = null;
        } else {
            reqInfo = object2string(args[0]);
        }
        LOG.info("[controller]invoke method={}, params={}", method, reqInfo);
        StopWatch clock = new StopWatch();
        clock.start();

        Object result = null;
        try {
            result = jp.proceed();
        } catch (BizException bz) {
            clock.stop();
            if (isFallback(bz.getErrorCode())) {
                FallbackConfig fallbackConfig = matchedControllerConfig(getRequestPath());
                if (fallbackConfig != null && isMatchParams(reqInfo, fallbackConfig.getParams())) {
                    LOG.warn("[controller]error invoke method={}, use fallbackConfig rsp={}, costs={}ms", method, bz,
                            clock.getTotalTimeMillis());
                    return fallbackConfig.getResultValue();
                }
            }
            
            LOG.warn("[controller]error invoke method={}, costs={}ms, ", method, clock.getTotalTimeMillis(), bz.toString());
            throw (Throwable)bz;
        } catch (Throwable e) {
            clock.stop();
            LOG.warn("[controller]error invoke method={}, costs={}ms, ", method, clock.getTotalTimeMillis(), e.toString());
            throw e;
        }
        
        String rspInfo = object2string(result);
        clock.stop();
        
        LOG.info("[controller]end invoke method={}, rsp={}, costs={}ms", method, rspInfo, clock.getTotalTimeMillis());
        return result;
    }

    private FallbackConfig matchedControllerConfig(String path) {
        List<FallbackConfig> fallbackConfigs = FallbackConfigHelper.getFallbackConfigMap().get(path);
        if (!CollectionUtils.isEmpty(fallbackConfigs)) {
            for (FallbackConfig config : fallbackConfigs) {
                if (config.getServiceName().equals(serviceName) && StringUtils.isEmpty(config.getClientName())) {
                    return config;
                }
            }
        }
        return null;
    }

    /**
     * 根据返回码判断服务调用是否降级。
     * @param result
     * @return
     */
    private boolean isFallback(String resultCode) {
        if (!FallbackConfigHelper.isUseFallbackConfig()){
            return false;
        }
        
        return BaseServiceFallback.FALLBACK_CODE.equals(resultCode);
    }
    
    private String getResultCodeFromRsp(Object result){
        if (result != null && result instanceof BaseRsp){
            return ((BaseRsp)result).getResult().getResultCode();
        }
        return null;
    }

    /**
     * fallback入参适配
     * 
     * @param reqInfo 请求中的参数
     * @param params 配置的fallback参数
     * @return 是否匹配
     */
    private boolean isMatchParams(String reqInfo, Map<String, String> params) {
        if (params == null || params.isEmpty()){
            return true;
        }
        
        for (Map.Entry<String,String> entry : params.entrySet()){
            if (!entry.getValue().equals(getParameterValue(reqInfo, entry.getKey()))){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据参数名称获取参数值。
     * 
     * @param reqInfo 请求信息
     * @param parameterName
     * @return
     */
    private static String getParameterValue(String reqInfo, String parameterName) {
        if (StringUtils.isEmpty(reqInfo)|| StringUtils.isEmpty(parameterName)){
            return null;
        }
        
        String temp = parameterName + "=";
        String subReq = reqInfo.substring(reqInfo.indexOf(temp) + temp.length());
        // QueryUserInfoReq(super=BaseReq(requestHeader=Header(version=1.0, transactionId=1520181108191226ebaXg6K2XuaZ7ukF, productLine=06, portalType=09, platform=04, companyId=02, imei=null, imsi=null, clientVer=null, appid=20500602, appName=null, extention=UID_94AFA50A46EB37203BC3A04FBEB496B6)), userId=1480290262755, dataSource=0, userAccountType=11, userAccount=UID_94AFA50A46EB37203BC3A04FBEB496B6)
        // 如上请求信息，参数名称=参数值，后面接","或")"
        int minIndex = minIndex(subReq.indexOf(","), subReq.indexOf(")"));
        if (minIndex != -1){
            return subReq.substring(0, minIndex);
        }
        
        return null;
    }
    
    private static int minIndex (int a, int b){
        return (a == -1) ? b: (a > b ? b : a);
    }

    /**
     * 方法被调用前执行.
     *
     * @param jp a join point
     * @throws Throwable 异常
     */
    @Around("client()")
    public Object aroundClient(final ProceedingJoinPoint jp) {
        Object[] args = jp.getArgs();
        String method = jp.getSignature().getName();
        String clientServiceName = getClientServiceName(jp);
       
        LOG.info("invoke client {} method={}, params={}", clientServiceName, method, args[0]);
        StopWatch clock = new StopWatch();
        clock.start();

        Object result = null;
        try {
            result = jp.proceed();
        } catch (Throwable e) {
            clock.stop();
            LOG.error("error when invoke client {} method={}, rsp={}, costs={}ms", clientServiceName, method, result, clock.getTotalTimeMillis(), e);
        }
        
        clock.stop();
        if (isFallback(getResultCodeFromRsp(result))){
            FallbackConfig fallbackConfig = matchedClientConfig(getRequestPath(), getClientPath(jp), clientServiceName);
            if (fallbackConfig != null && isMatchParams(args[0].toString(), fallbackConfig.getParams())){
                LOG.warn("end invoke client {} method={}, use fallbackConfig rsp={}, costs={}ms", clientServiceName, method, result, clock.getTotalTimeMillis());
                return fallbackConfig.getResultValue();
            }
        }
        
        LOG.info("end invoke client {} method={}, rsp={}, costs={}ms", clientServiceName, method, result, clock.getTotalTimeMillis());
        return result;
    }

    private String getClientPath(final ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        String clientPath = null;
        try {
            clientPath = methodSignature.getMethod().getAnnotation(RequestMapping.class).value()[0];
        } catch (Exception e) {
            LOG.error("error when getClientPath, ", e);
        }
        return clientPath;
    }
    
    private String getRequestPath() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request == null ? null : request.getRequestURI();
    }
    
    /**
     * 获取client的类名称
     * @param jp
     * @return
     */
    @SuppressWarnings("unused")
    private String getClientClassName(ProceedingJoinPoint jp) {
        for (Type t : jp.getTarget().getClass().getGenericInterfaces()){
            if (t.getTypeName().startsWith(FallbackFactory.class.getName())){
                return t.getTypeName().substring(t.getTypeName().lastIndexOf(".") + 1, t.getTypeName().length() - 1);
            }
        }
        return null;
    }

    private FallbackConfig matchedClientConfig(String controllerPath, String clientPath, String clientServiceName) {
        if (StringUtils.isEmpty(controllerPath) || StringUtils.isEmpty(clientPath) || StringUtils.isEmpty(clientServiceName)){
            return null;
        }
        
        List<FallbackConfig> fallbackConfigs = FallbackConfigHelper.getFallbackConfigMap().get(controllerPath);
        if (!CollectionUtils.isEmpty(fallbackConfigs)) {
            for (FallbackConfig config : fallbackConfigs) {
                if (config.getServiceName().equals(serviceName) && clientServiceName.equals((config.getClientName()))
                        && config.getClientPath().equals(clientPath)) {
                    return config;
                }
            }
        }
        return null;
    }
    
    /**
     * 获取client 的服务名
     *
     * @return clientName
     */
    protected String getClientServiceName(ProceedingJoinPoint jp) {
        String clientServiceName = "";
        try {
            String s = jp.getTarget().toString();
            if (s.contains("HardCodedTarget") && s.contains("name")) {
                int i = s.indexOf(",");
                String s1 = s.substring(i + 1);
                String s2 = s1.substring(0, s1.indexOf(","));
                clientServiceName = s2.split("=")[1];
            }
        } catch (Throwable e) {
            LOG.warn("error when getClientServiceName, ", e.toString());
        }

        return clientServiceName;
    }

    /***
     * Object转换String,去掉XML格式的空格
     * @Description: object2string
     * @Param: [obj]
     * @return
     */
    protected String object2string(final Object obj) {
        String reqInfo = null;
        if (!StringUtils.isEmpty(obj)) {
            if (obj instanceof String) {
                String reqString = (String) obj;
                
                reqInfo = reqString;
//                if (reqString.startsWith("<?xml")) {
//                    reqInfo = JaxbUtil.removeBlank(reqString);
//                } else {
//                    reqInfo = reqString;
//                }
            } else {
                reqInfo = obj.toString();
            }
        }
        return reqInfo;
    }
}
