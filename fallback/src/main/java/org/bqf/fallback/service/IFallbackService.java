package org.bqf.fallback.service;

import org.bqf.common.dto.inside.GetFallbackConfigReq;
import org.bqf.common.dto.inside.GetFallbackConfigRsp;
import org.bqf.fallback.dto.DeleteFallbackConfigReq;
import org.bqf.fallback.dto.DeleteFallbackConfigRsp;
import org.bqf.fallback.dto.FallbackConfigReq;
import org.bqf.fallback.dto.FallbackConfigRsp;

public interface IFallbackService {

    FallbackConfigRsp addConfig(final FallbackConfigReq req);
    
    FallbackConfigRsp updateConfig(final FallbackConfigReq req);
    
    DeleteFallbackConfigRsp deleteConfig(final DeleteFallbackConfigReq req); 
    
    GetFallbackConfigRsp getConfig(final GetFallbackConfigReq req);
}
