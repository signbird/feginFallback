package org.bqf.test.client;

import org.bqf.test.client.fallback.ContentQueryFallback1;
import org.bqf.test.dto.inside.GetContentReq;
import org.bqf.test.dto.inside.GetContentRsp;
import org.bqf.test.dto.inside.GetPlayInfoReq;
import org.bqf.test.dto.inside.GetPlayInfoRsp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "content-query", fallbackFactory = ContentQueryFallback1.class)
@Qualifier("contentQueryClient")
public interface ContentQueryClient {

    @RequestMapping(value = "/contentCenter/getContent", produces = { "application/json" }, method = RequestMethod.POST)
    GetContentRsp getContent(@RequestBody GetContentReq req);

    @RequestMapping(value = "/contentCenter/getPlayInfo", produces = {"application/json" }, method = RequestMethod.POST)
    GetPlayInfoRsp getPlayInfo(@RequestBody GetPlayInfoReq req);
}
