package org.bqf.test.client.fallback;

import org.bqf.test.client.ContentQueryClient;
import org.bqf.test.dto.inside.GetContentReq;
import org.bqf.test.dto.inside.GetContentRsp;
import org.bqf.test.dto.inside.GetPlayInfoReq;
import org.bqf.test.dto.inside.GetPlayInfoRsp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
//@Qualifier("contentQueryClient1")
@AllArgsConstructor
@NoArgsConstructor
public class ContentQueryFallback1 extends BaseServiceFallback implements FallbackFactory<ContentQueryClient>, ContentQueryClient {

    private Throwable cause;

    @Override
    public ContentQueryClient create(Throwable cause) {
        return new ContentQueryFallback1(cause);
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