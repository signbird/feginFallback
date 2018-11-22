package org.bqf.common.client.fallback;

import org.bqf.common.client.FallbackServiceClient;
import org.bqf.common.dto.inside.GetFallbackConfigReq;
import org.bqf.common.dto.inside.GetFallbackConfigRsp;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class FallbackServiceFallback extends BaseServiceFallback implements FallbackFactory<FallbackServiceClient>, FallbackServiceClient {

    private Throwable cause;

    @Override
    public FallbackServiceClient create(Throwable cause) {
        return new FallbackServiceFallback(cause);
    }

    @Override
    public GetFallbackConfigRsp getConfig(GetFallbackConfigReq req) {
        callServiceFallbackCause(Thread.currentThread().getStackTrace()[1].getMethodName(), cause);
        GetFallbackConfigRsp response = new GetFallbackConfigRsp();
        response.setResult(fallbackResult(Thread.currentThread().getStackTrace()[1].getMethodName()));
        return response;
    }

}