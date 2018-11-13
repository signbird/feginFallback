package org.bqf.test.service.impl;

import org.bqf.test.client.ContentQueryClient;
import org.bqf.test.dto.inside.GetContentReq;
import org.bqf.test.dto.inside.GetContentRsp;
import org.bqf.test.dto.outside.GetPlayDownloadUrlReqOut;
import org.bqf.test.dto.outside.GetPlayDownloadUrlRspOut;
import org.bqf.test.dto.outside.QueryContentReqOut;
import org.bqf.test.dto.outside.QueryContentRspOut;
import org.bqf.test.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl extends BaseServiceImpl implements IContentService {

    @Qualifier("contentQueryClient2")
    @Autowired
    private ContentQueryClient client2;
    
    @Qualifier("contentQueryClient")
    @Autowired
    private ContentQueryClient client1;

    @Override
    public QueryContentRspOut queryContent(QueryContentReqOut req) {
        System.out.println("before client invoke getContent...");
        
        GetContentReq contentReq = new GetContentReq(req.getRequestHeader(), req.getContentCode());
        GetContentRsp contentRsp = client1.getContent(contentReq);
        callRemoteServiceResult("contentService", "getContent", contentRsp.getResult());
        return new QueryContentRspOut(contentRsp.getResult(), contentRsp.getContent());
    }

    @Override
    public GetPlayDownloadUrlRspOut getPlayDownloadUrl(GetPlayDownloadUrlReqOut req) {
        System.out.println("before client invoke getPlayDownloadUrl...");
        return null;
    }
}
