package org.bqf.service.service.impl;

import org.bqf.common.client.ContentQueryClient;
import org.bqf.common.dto.inside.GetContentReq;
import org.bqf.common.dto.inside.GetContentRsp;
import org.bqf.common.dto.outside.GetPlayDownloadUrlReqOut;
import org.bqf.common.dto.outside.GetPlayDownloadUrlRspOut;
import org.bqf.common.dto.outside.QueryContentReqOut;
import org.bqf.common.dto.outside.QueryContentRspOut;
import org.bqf.service.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl extends BaseServiceImpl implements IContentService {

//    @Qualifier("contentQueryClient2")
//    @Autowired
//    private ContentQueryClient client2;
    
    @Qualifier("contentQueryClient")
    @Autowired
    private ContentQueryClient contentQueryClient;

    @Override
    public QueryContentRspOut queryContent(QueryContentReqOut req) {
        System.out.println("before client invoke getContent...");
        
        GetContentReq contentReq = new GetContentReq(req.getRequestHeader(), req.getContentCode());
        GetContentRsp contentRsp = contentQueryClient.getContent(contentReq);
        callRemoteServiceResult("contentService", "getContent", contentRsp.getResult());
        return new QueryContentRspOut(contentRsp.getResult(), contentRsp.getContent());
    }

    @Override
    public GetPlayDownloadUrlRspOut getPlayDownloadUrl(GetPlayDownloadUrlReqOut req) {
        System.out.println("before client invoke getPlayDownloadUrl...");
        return null;
    }
}
