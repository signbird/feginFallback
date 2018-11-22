package org.bqf.common.client;

import org.bqf.common.client.fallback.ContentQueryFallback;
import org.bqf.common.dto.inside.GetContentReq;
import org.bqf.common.dto.inside.GetContentRsp;
import org.bqf.common.dto.inside.GetPlayInfoReq;
import org.bqf.common.dto.inside.GetPlayInfoRsp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-client", fallbackFactory = ContentQueryFallback.class)
@Qualifier("contentQueryClient")
public interface ContentQueryClient {

    @RequestMapping(value = "/contentCenter/getContent", produces = { "application/json" }, method = RequestMethod.POST)
    GetContentRsp getContent(@RequestBody GetContentReq req);

    @RequestMapping(value = "/contentCenter/getPlayInfo", produces = {"application/json" }, method = RequestMethod.POST)
    GetPlayInfoRsp getPlayInfo(@RequestBody GetPlayInfoReq req);
}
