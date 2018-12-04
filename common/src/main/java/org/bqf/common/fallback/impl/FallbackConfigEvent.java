package org.bqf.common.fallback.impl;

import java.util.List;

import org.bqf.common.client.FallbackServiceClient;
import org.bqf.common.client.fallback.BaseServiceFallback;
import org.bqf.common.dto.inside.GetFallbackConfigReq;
import org.bqf.common.dto.inside.GetFallbackConfigRsp;
import org.bqf.common.fallback.FallbackConfig;
import org.bqf.common.fallback.FallbackConfigHelper;
import org.bqf.common.util.HeaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * spring启动后,请求fallback服务获取降级配置信息。
 * 
 */
@Component
public class FallbackConfigEvent implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(FallbackConfigEvent.class);
    
    @Autowired
    private FallbackServiceClient fallbackServiceClient;
    
    @Value("${spring.application.name}")
    private String serviceName;
    
//    @Autowired
//    private RestTemplate restTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // spring web项目下，可能会造成二次执行，因为此时系统会存在两个容器，一个是spring本身的root application context，
        // 另一个是servlet容器（作为spring容器的子容器，projectName-servlet context），此时，加以下限制条件规避:
        if (event.getApplicationContext().getParent().getParent() == null) {
            // 只有root application context 没有父容器
            System.out.println("-------------------common event启动后加载  onApplicationEvent-------------");

            if (FallbackConfigHelper.isUseFallbackConfig()) {
                new InitFallbackConfigThread().run();
            }
        }
    }
    
    public class InitFallbackConfigThread implements Runnable {

        @Override
        public void run() {
            GetFallbackConfigReq req = new GetFallbackConfigReq();
            req.setRequestHeader(HeaderFactory.getHeader());
            req.setServiceName(serviceName);
            
//            StringBuilder callUrl = new StringBuilder();
//            callUrl.append("http://").append("SERVICE-FALLBACK").append("/fallback/getConfig");
//            GetFallbackConfigRsp rsp1 = restTemplate.postForObject(callUrl.toString(), req, GetFallbackConfigRsp.class);
//            System.out.println(rsp1);
            
            GetFallbackConfigRsp rsp = fallbackServiceClient.getConfig(req);
            if (BaseServiceFallback.FALLBACK_CODE.equals(rsp.getResult().getResultCode())){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                rsp = fallbackServiceClient.getConfig(req);
            }
            
            if (rsp == null || rsp.getResult() == null || !rsp.getResult().getResultCode().endsWith("00000")){
                LOG.warn("error when invoke fallback.getConfig, rsp={}", rsp);
            }
            
            initCache(rsp);
            LOG.info("end init fallback config, configs={}", rsp.getConfigs());
        }

        private void initCache(GetFallbackConfigRsp rsp) {
            List<FallbackConfig> configs = rsp.getConfigs();
            if (!CollectionUtils.isEmpty(configs)){
                for (FallbackConfig config : configs){
                    if (config.getResultValue() != null){
                        // fastJson将config中具体的Object类型反序列化成了JSONObject，需要再转回来。
                        Object resultObj = JSONObject.parseObject(config.getResultValue().toString(), config.getResultClass());
                        config.setResultValue(resultObj);
                    }
                    FallbackConfigHelper.addConfig(config);
                }
            }
        }
    }
}


