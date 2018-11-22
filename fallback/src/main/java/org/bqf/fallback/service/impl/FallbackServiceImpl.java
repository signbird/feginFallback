package org.bqf.fallback.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bqf.common.consts.FallbackResultCode;
import org.bqf.common.dto.Result;
import org.bqf.common.dto.inside.FallbackConfigNotifyReq;
import org.bqf.common.dto.inside.FallbackConfigNotifyRsp;
import org.bqf.common.dto.inside.GetFallbackConfigReq;
import org.bqf.common.dto.inside.GetFallbackConfigRsp;
import org.bqf.common.fallback.FallbackConfig;
import org.bqf.common.fallback.FallbackConfigHelper;
import org.bqf.fallback.client.FallbackConfigNotifyClient;
import org.bqf.fallback.dao.FallbackConfigDao;
import org.bqf.fallback.dao.model.FallbackConfigModel;
import org.bqf.fallback.dto.DeleteFallbackConfigReq;
import org.bqf.fallback.dto.DeleteFallbackConfigRsp;
import org.bqf.fallback.dto.FallbackConfigReq;
import org.bqf.fallback.dto.FallbackConfigRsp;
import org.bqf.fallback.service.IFallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

@Service
public class FallbackServiceImpl implements IFallbackService, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(FallbackServiceImpl.class);
    
    @Autowired
    private FallbackConfigDao fallbackDao;
    
    @Qualifier("fallbackConfigNotifyClient")
    @Autowired
    private FallbackConfigNotifyClient notifyClient;

    // TODO add/update/delete 事务支持
    @Override
    public FallbackConfigRsp addConfig(FallbackConfigReq req) {
        Result baseRsp = new Result();
        FallbackConfig config = checkAndConvert(req.getFallbackConfig(), baseRsp);
        if (FallbackResultCode.SUCCESS.getCode().equals(baseRsp.getResultCode())){
            if (FallbackConfigHelper.containsConfig(config)){
                FallbackConfigModel model = req.getFallbackConfig();
                model.setId(config.getId());
                fallbackDao.update(model);
                FallbackConfigHelper.updateConfig(config);
                baseRsp = invokeNotify(config, FallbackConfigNotifyReq.NOTIFY_TPYE_UPDATE);
            } else {
                FallbackConfigModel fallbackModel = req.getFallbackConfig();
                fallbackDao.insert(fallbackModel);
                config.setId(fallbackModel.getId());
                FallbackConfigHelper.addConfig(config);
                baseRsp = invokeNotify(config, FallbackConfigNotifyReq.NOTIFY_TPYE_ADD);
            }
        }
        
        return new FallbackConfigRsp(baseRsp);
    }

    // TODO 多个fallback进程下，还需要通知其他进程
    // 可以在fallback上开接口，通过eureka获取其他实例的地址，并且要判断不能发给自己
    // 或者fallback上干脆不做缓存 都走db  就不用通知其他进程了
    private Result invokeNotify(FallbackConfig config, String type) {
        FallbackConfigNotifyReq notifyReq = new FallbackConfigNotifyReq();
        notifyReq.setNofityType(type);
        notifyReq.setFallbackConfig(config);
        FallbackConfigNotifyRsp notifyRsp = notifyClient.notify(notifyReq);
        return notifyRsp.getResult();
    }

    @Override
    public FallbackConfigRsp updateConfig(FallbackConfigReq req) {
        FallbackConfigModel configModel = req.getFallbackConfig();
        if (StringUtils.isEmpty(configModel.getId())) {
            return new FallbackConfigRsp(
                    new Result(FallbackResultCode.PARA_VALIDATE_ERROR.getCode(), "fallbackConfig.id cannot be null"));
        }

        Result baseRsp = new Result();
        FallbackConfig config = checkAndConvert(req.getFallbackConfig(), baseRsp);
        if (FallbackResultCode.SUCCESS.getCode().equals(baseRsp.getResultCode())){
            int updateResult = FallbackConfigHelper.updateConfig(config);
            if (updateResult == -1){
                return new FallbackConfigRsp(
                        new Result(FallbackResultCode.PARA_VALIDATE_ERROR.getCode(), "fallbackConfig not exist"));
            }else {
                fallbackDao.update(req.getFallbackConfig());
                baseRsp = invokeNotify(config, FallbackConfigNotifyReq.NOTIFY_TPYE_UPDATE);
            }
        }
        
        return new FallbackConfigRsp(baseRsp);
    }

    @Override
    public DeleteFallbackConfigRsp deleteConfig(DeleteFallbackConfigReq req) {
        DeleteFallbackConfigRsp rsp = new DeleteFallbackConfigRsp();
        String configId = req.getConfigId();
        String serviceName = req.getServiceName();
        String servicePath = req.getServicePath();
        FallbackConfig config  = new FallbackConfig();
        config.setId(configId);
        config.setServiceName(serviceName);
        config.setServicePath(servicePath);
        
        Result baseRsp = null;
        if (!StringUtils.isEmpty(configId)){
            FallbackConfigHelper.deleteById(configId);
            fallbackDao.deleteById(configId);
        } else if (!StringUtils.isEmpty(serviceName) && StringUtils.isEmpty(servicePath)){
            FallbackConfigHelper.deleteByServiceName(serviceName);
            fallbackDao.deleteByServiceName(serviceName);
        } else if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(servicePath)){
            FallbackConfigHelper.deleteByServicePath(serviceName, servicePath);
            fallbackDao.deleteByServicePath(serviceName, servicePath);
        } else {
            baseRsp = new Result(FallbackResultCode.PARA_VALIDATE_ERROR.getCode(), "input params invalid");
            rsp.setResult(baseRsp);
            return rsp;
        }
        baseRsp = invokeNotify(config, FallbackConfigNotifyReq.NOTIFY_TPYE_DELETE);
        rsp.setResult(baseRsp);
        return rsp;
    }

    @Override
    public GetFallbackConfigRsp getConfig(GetFallbackConfigReq req) {
        GetFallbackConfigRsp rsp = new GetFallbackConfigRsp();
        rsp.setResult(new Result(FallbackResultCode.SUCCESS.getCode(), FallbackResultCode.SUCCESS.getMsg()));

        List<FallbackConfig> configs = FallbackConfigHelper.getAllFallbackConfig();
        String serviceName = req.getServiceName();
        if (StringUtils.isEmpty(serviceName)) {
            rsp.setConfigs(configs);
        } else {
            List<FallbackConfig> serviceConfig = new ArrayList<>();
            for (FallbackConfig c : configs) {
                if (serviceName.equals(c.getServiceName())) {
                    serviceConfig.add(c);
                }
            }
            rsp.setConfigs(serviceConfig);
        }

        return rsp;
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<FallbackConfigModel> dbConfigs = fallbackDao.selectAll();
        
        if (dbConfigs == null){
            LOG.warn("no fallbackConfig get from db.");
            return ;
        }
        
        for (FallbackConfigModel model : dbConfigs){
            FallbackConfig config = checkAndConvert(model, new Result());
            if (config != null){
                FallbackConfigHelper.addConfig(config);
            }
        }
        
        System.out.println("-------------------fallback启动后加载  onApplicationEvent-------------");
    }
    
    
    private FallbackConfig checkAndConvert(FallbackConfigModel model, Result baseRsp) {
        if (!isValidService(model)){
            LOG.error("fallbackConfig from db is invalid, fallbackConfig={}", model);
            baseRsp.setResultCode(FallbackResultCode.FALLBACK_CONFIG_INVALID.getCode());
            baseRsp.setResultMessage("serviceName [" + model.getServiceName() + "] not exist");
            return null;
        }
        
        Class<?> resultClazz = null;
        try {
            resultClazz = Class.forName(model.getResultClass());
        } catch (ClassNotFoundException e) {
            LOG.error("error parseClass fallbackConfig.resultClass={}, fallbackConfig={}", model.getResultClass(), model);
            baseRsp.setResultCode(FallbackResultCode.FALLBACK_CONFIG_INVALID.getCode());
            baseRsp.setResultMessage(FallbackResultCode.FALLBACK_CONFIG_INVALID.getMsg());
            return null;
        }
        Object resultObj = JSONObject.parseObject(model.getResultValue(), resultClazz);
        if (resultObj == null){
            LOG.error("error parseJson fallbackConfig.resultValue={}, fallbackConfig={}", model.getResultValue(), model);
            baseRsp.setResultCode(FallbackResultCode.FALLBACK_CONFIG_INVALID.getCode());
            baseRsp.setResultMessage(FallbackResultCode.FALLBACK_CONFIG_INVALID.getMsg());
            return null;
        }
        Map<String, String> params = convertParams(model.getParams());
        LOG.info("parsed params={}, fallbackConfig={}", params, model);
        
        FallbackConfig config = new FallbackConfig();
        config.setId(model.getId());
        config.setServiceName(model.getServiceName());
        config.setServicePath(model.getServicePath());
        config.setClientName(model.getClientName());
        config.setClientPath(model.getClientPath());
        config.setParams(params);
        config.setResultClass(resultClazz);
        config.setResultValue(resultObj);
        config.setLevel(model.getLevel());
        
        baseRsp.setResultCode(FallbackResultCode.SUCCESS.getCode());
        baseRsp.setResultMessage(FallbackResultCode.SUCCESS.getMsg());
        return config;
    }

    private static Map<String, String> convertParams(String params) {
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        String[] splits = params.split(",");
        Map<String, String> paramsMap = new HashMap<>();
        for (String s : splits) {
            if (s.contains("=")) {
                int index = s.indexOf("=");
                String key = s.substring(0, index);
                String value = s.substring(index + 1, s.length());
                if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                    paramsMap.put(key, value);
                }
            }
        }
        return paramsMap;
    }

    private boolean isValidService(FallbackConfigModel model) {
        if (model == null || StringUtils.isEmpty(model.getServiceName()) || StringUtils.isEmpty(model.getServicePath())){
            return false;
        }
        
        // 不校验serviceName。 因为fallback启动时其他服务可能并未注册到eureka，或者恰好某个服务下线
//        List<String> servcies = discoveryClient.getServices();
//        if (!servcies.contains(model.getServiceName())){
//            return false;
//        }
        
        return true;
    }

}
