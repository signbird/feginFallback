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

//@Component
//@Qualifier("contentQueryClient2")
//@AllArgsConstructor
//@NoArgsConstructor
//public class ContentQueryFallback2 implements FallbackFactory<ContentQueryClient>, ContentQueryClient {
//
//    private Throwable cause;
//
//    @Override
//    public ContentQueryClient create(Throwable cause) {
//        return new ContentQueryFallback1(cause);
//    }
//
//    @Override
//    public GetContentRsp getContent(GetContentReq req) {
//        System.out.println("reason was:" + cause);
//        return new GetContentRsp("fallback22");
//    }
//
//    @Override
//    public GetPlayInfoRsp getPlayInfo(GetPlayInfoReq req) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//}
