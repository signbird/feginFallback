package org.bqf.fallback.client;

import org.bqf.common.dto.inside.FallbackConfigNotifyReq;
import org.bqf.common.dto.inside.FallbackConfigNotifyRsp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;

// TODO 需要实现为  通过eureka请求所有节点的服务
@Qualifier("fallbackConfigNotifyClient")
public interface FallbackConfigNotifyClient {

    FallbackConfigNotifyRsp notify(@RequestBody final FallbackConfigNotifyReq req);
}