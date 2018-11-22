package org.bqf.service.service;

import org.bqf.common.dto.outside.GetPlayDownloadUrlReqOut;
import org.bqf.common.dto.outside.GetPlayDownloadUrlRspOut;
import org.bqf.common.dto.outside.QueryContentReqOut;
import org.bqf.common.dto.outside.QueryContentRspOut;

public interface IContentService {

    QueryContentRspOut queryContent(QueryContentReqOut req);
    
    GetPlayDownloadUrlRspOut getPlayDownloadUrl(GetPlayDownloadUrlReqOut req);
}
