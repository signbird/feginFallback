package org.bqf.test.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * RequestMapping工具类，需要通过@Autowired注解到使用类中。
 * 创建人: 白秋飞
 * 创建时间: 2018/9/30
 */
@Configuration
public class RequestMappingHelper {

    private static final Logger LOG = LoggerFactory.getLogger(RequestMappingHelper.class);
    
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }
    
    /**
     * 根据方法名获取RequestMapping注解的相对路径
     * 
     * @param methodName
     * @return 相对路径， 例如： /general/applepay/iOSVerifyReceipt
     */
    public String getMappingUrl(String methodName) {
        if (StringUtils.isEmpty(methodName)) {
            return null;
        }

        RequestMappingHandlerMapping mapping = requestMappingHandlerMapping();
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();

        for (RequestMappingInfo info : methodMap.keySet()) {
            HandlerMethod method = methodMap.get(info);
            if (methodName.equals(method.getMethod().getName())) {
                String requestUrl = info.getPatternsCondition().toString();
                if (!StringUtils.isEmpty(requestUrl)) {
                    return requestUrl.substring(1, requestUrl.length() - 1);
                }
            }
        }

        LOG.warn("can't get requestUrl by RequestMapping annotation, method={}", methodName);
        return null;
    }

}
