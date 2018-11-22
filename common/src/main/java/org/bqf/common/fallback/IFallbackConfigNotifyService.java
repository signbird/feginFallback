package org.bqf.common.fallback;

import org.bqf.common.dto.inside.FallbackConfigNotifyReq;
import org.bqf.common.dto.inside.FallbackConfigNotifyRsp;

public interface IFallbackConfigNotifyService {

    FallbackConfigNotifyRsp notify(final FallbackConfigNotifyReq req);
    
}
