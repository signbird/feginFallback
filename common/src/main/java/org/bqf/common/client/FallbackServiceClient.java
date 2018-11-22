package org.bqf.common.client;

import org.bqf.common.client.fallback.FallbackServiceFallback;
import org.bqf.common.dto.inside.GetFallbackConfigReq;
import org.bqf.common.dto.inside.GetFallbackConfigRsp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "fallback", fallbackFactory = FallbackServiceFallback.class)
@Qualifier("fallbackServiceClient")
public interface FallbackServiceClient {
    
    @RequestMapping(value = "/fallback/getConfig", produces = { "application/json" }, method = RequestMethod.POST)
    GetFallbackConfigRsp getConfig(@RequestBody final GetFallbackConfigReq req);

}
