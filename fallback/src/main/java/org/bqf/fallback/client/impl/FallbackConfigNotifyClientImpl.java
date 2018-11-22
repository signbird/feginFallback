package org.bqf.fallback.client.impl;

import java.util.List;

import org.bqf.common.consts.FallbackResultCode;
import org.bqf.common.dto.Result;
import org.bqf.common.dto.inside.FallbackConfigNotifyReq;
import org.bqf.common.dto.inside.FallbackConfigNotifyRsp;
import org.bqf.fallback.client.FallbackConfigNotifyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Qualifier("fallbackConfigNotifyClient")
public class FallbackConfigNotifyClientImpl implements FallbackConfigNotifyClient{

    private static final Logger LOG = LoggerFactory.getLogger(FallbackConfigNotifyClientImpl.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    @Value("${spring.application.name}")
    private String fallbackServiceName;
    
    /**
     * 各服务的notify请求url， 在common包中的FallbackConfigNotifyController中定义
     */
    private static final String NOTIFY_URL = "/common/fallback/notify";
    
    @Override
    public FallbackConfigNotifyRsp notify(FallbackConfigNotifyReq req) {
        List<String> servcieNames = discoveryClient.getServices();
        LOG.info("get serviceNames from eureka, serviceNames={}", servcieNames.toString());
        
        // TODO 异步调用？
        for (String serviceName : servcieNames) {
            if (serviceName.equals(req.getFallbackConfig().getServiceName())) {
                List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
                for (ServiceInstance si : serviceInstances) {
                    
                    // TODO 容器环境下 eureka获取的这个ip 是否可直接调用？ 待验证
                    String reqUrl = si.getUri() + NOTIFY_URL;
                    FallbackConfigNotifyRsp subRsp = restTemplate.postForObject(reqUrl, req, FallbackConfigNotifyRsp.class);
                    
                    if (subRsp == null
                            || !FallbackResultCode.SUCCESS.getCode().equals(subRsp.getResult().getResultCode())) {
                        LOG.error("error notify {}, serviceName={}, rsp={}, fallbackConfigNotify={}", reqUrl,
                                serviceName, subRsp, req);
                    } else {
                        LOG.info("success notify {}, serviceName={}, fallbackConfigNotify={}", reqUrl, serviceName, req);
                    }
                    
                    // TODO 配置更新结果写入数据库？
                }
            }
        }
        
        return new FallbackConfigNotifyRsp(new Result(FallbackResultCode.SUCCESS.getCode(), FallbackResultCode.SUCCESS.getMsg()));
    }

    public void getRegistered(){
        List<String> servcies = discoveryClient.getServices();
        System.out.println("servcies = " + servcies);
        
        for( String s : servcies){
            System.out.println("services " + s);
            List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(s);
            for(ServiceInstance si : serviceInstances){
                System.out.println("    services:" + s + ":getHost()=" + si.getHost());
                System.out.println("    services:" + s + ":getPort()=" + si.getPort());
                System.out.println("    services:" + s + ":getServiceId()=" + si.getServiceId());
                System.out.println("    services:" + s + ":getUri()=" + si.getUri());
                System.out.println("    services:" + s + ":getMetadata()=" + si.getMetadata());
            }
        }
    }
}
