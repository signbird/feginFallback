package org.bqf.service.service.impl;

import org.bqf.common.dto.Result;
import org.bqf.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 调用远程方法输出日志，并判断结果，如果失败则抛出异常.
     *
     * @param service
     *            服务名
     * @param method
     *            远程方法名
     * @param result
     *            结果
     */
    protected void callRemoteServiceResult(final String service, final String method, final Result result)
            throws BizException {

        if (result == null) {
            LOG.warn("error when invoke {}.{}, result is null", service, method);
            StringBuilder sb = new StringBuilder();
            sb.append(service).append(".").append(method).append(" Result is null");
            throw new BizException("8000000009", sb.toString());
        }

//        // 服务降级不抛异常，以便返回可配的降级结果
//        if (BaseServiceFallback.FALLBACK_CODE.equals(result.getResultCode())) {
//            LOG.warn("fallback when invoke {}.{}, result is {}:{}", service, method, result.getResultCode(),
//                    result.getResultMessage());
//            return ;
//        }
        
        if (!result.getResultCode().endsWith("00000")) {
            LOG.warn("error when invoke {}.{}, result is {}:{}", service, method, result.getResultCode(),
                    result.getResultMessage());
            throw new BizException(result.getResultCode(), result.getResultMessage());
        }
    }
}