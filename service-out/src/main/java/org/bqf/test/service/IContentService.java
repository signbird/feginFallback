package org.bqf.test.service;

import org.bqf.test.dto.outside.GetPlayDownloadUrlReqOut;
import org.bqf.test.dto.outside.GetPlayDownloadUrlRspOut;
import org.bqf.test.dto.outside.QueryContentReqOut;
import org.bqf.test.dto.outside.QueryContentRspOut;

public interface IContentService {

    QueryContentRspOut queryContent(QueryContentReqOut req);
    
    GetPlayDownloadUrlRspOut getPlayDownloadUrl(GetPlayDownloadUrlReqOut req);
}
