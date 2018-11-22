package org.bqf.common.client.fallback;

import org.bqf.common.client.ContentQueryClient;
import org.bqf.common.dto.inside.GetContentReq;
import org.bqf.common.dto.inside.GetContentRsp;
import org.bqf.common.dto.inside.GetPlayInfoReq;
import org.bqf.common.dto.inside.GetPlayInfoRsp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Qualifier("contentQueryClient")
public class ContentQueryFallback extends BaseServiceFallback implements FallbackFactory<ContentQueryClient>, ContentQueryClient {

    private Throwable cause;

    @Override
    public ContentQueryClient create(Throwable cause) {
        return new ContentQueryFallback(cause);
    }

    @Override
    public GetContentRsp getContent(final GetContentReq queryContentReq) {
        callServiceFallbackCause(Thread.currentThread().getStackTrace()[1].getMethodName(), cause);
        GetContentRsp response = new GetContentRsp();
        response.setResult(fallbackResult(Thread.currentThread().getStackTrace()[1].getMethodName()));
        return response;
    }

    @Override
    public GetPlayInfoRsp getPlayInfo(final GetPlayInfoReq getPlayDownloadReq) {
        callServiceFallbackCause(Thread.currentThread().getStackTrace()[1].getMethodName(), cause);
        GetPlayInfoRsp response = new GetPlayInfoRsp();
        response.setResult(fallbackResult(Thread.currentThread().getStackTrace()[1].getMethodName()));
        return response;
    }
}