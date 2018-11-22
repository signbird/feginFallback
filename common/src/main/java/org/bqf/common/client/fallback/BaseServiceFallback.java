package org.bqf.common.client.fallback;

import org.bqf.common.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 入口类
 * 项目名称: Rainbow Stone for cartoon
 * 包: com.migu.rstone.client.fallback
 * 类名称: BaseServiceFallback.java
 * 类描述: 服务降级类
 * 创建人: waixie
 * 创建时间: 2017/8/3 10:33
 */
public class BaseServiceFallback {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseServiceFallback.class);
    
    public static final String FALLBACK_CODE = "00001";
    public static final String FALLBACK_MSG = "服务降级返回默认结果";
    
    protected static Result fallbackResult(final String methodName) {
        return new Result(FALLBACK_CODE, methodName + FALLBACK_MSG);
    }
    
    /**
     * 返回为何降级信息
     * @param cause
     */
    protected void callServiceFallbackCause(final String methodName, final Throwable cause) {
        if (cause!= null){
            String errorInfo = StringUtils.isEmpty(cause.getMessage()) ? cause.toString() : cause.getMessage();
            LOG.error("{} fallback. Reason cause was : {} ", methodName, errorInfo);
        }
    }
    /**
     * 返回为何降级信息forXml
     * @param methodName
     * @return
     * @author huadq
     */
    protected String  fallbackForXml(final String methodName) {
        return FALLBACK_CODE + methodName + FALLBACK_MSG;
    }
}
