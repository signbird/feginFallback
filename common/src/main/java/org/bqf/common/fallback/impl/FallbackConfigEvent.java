package org.bqf.common.fallback.impl;

import java.util.List;

import org.bqf.common.client.FallbackServiceClient;
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        System.out.println("-------------------common event启动后加载  onApplicationEvent-------------");
        
        if (FallbackConfigHelper.isUseFallbackConfig()){
            GetFallbackConfigReq req = new GetFallbackConfigReq();
            req.setRequestHeader(HeaderFactory.getHeader());
            req.setServiceName(serviceName);
            
            GetFallbackConfigRsp rsp = fallbackServiceClient.getConfig(req);
            if (rsp == null || rsp.getResult() == null || !rsp.getResult().getResultCode().endsWith("00000")){
                LOG.warn("error when invoke fallback.getConfig, rsp={}", rsp);
            }
            
            List<FallbackConfig> configs = rsp.getConfigs();
            if (!CollectionUtils.isEmpty(configs)){
                for (FallbackConfig config : configs){
                    FallbackConfigHelper.addConfig(config);
                }
            }
        }
    }
}
