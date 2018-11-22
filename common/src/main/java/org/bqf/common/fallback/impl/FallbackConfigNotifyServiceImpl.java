package org.bqf.common.fallback.impl;

import org.bqf.common.consts.FallbackResultCode;
import org.bqf.common.dto.Result;
import org.bqf.common.dto.inside.FallbackConfigNotifyReq;
import org.bqf.common.dto.inside.FallbackConfigNotifyRsp;
import org.bqf.common.fallback.FallbackConfig;
import org.bqf.common.fallback.FallbackConfigHelper;
import org.bqf.common.fallback.IFallbackConfigNotifyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FallbackConfigNotifyServiceImpl implements IFallbackConfigNotifyService{

    // TODO 入口权限校验 + swagger上隐藏  （参数校验？。。。）
    @Override
    public FallbackConfigNotifyRsp notify(FallbackConfigNotifyReq req) {

        String notifyType = req.getNofityType();
        FallbackConfig config = req.getFallbackConfig();
        
        FallbackConfigNotifyRsp rsp = new FallbackConfigNotifyRsp(
                new Result(FallbackResultCode.SUCCESS.getCode(), FallbackResultCode.SUCCESS.getMsg()));
        
        switch (notifyType) {
        case FallbackConfigNotifyReq.NOTIFY_TPYE_ADD:
            FallbackConfigHelper.addConfig(config);
            break;
        case FallbackConfigNotifyReq.NOTIFY_TPYE_UPDATE:
            FallbackConfigHelper.updateConfig(config);
            break;
        case FallbackConfigNotifyReq.NOTIFY_TPYE_DELETE:
            deleteConfig(config);
            break;
        default:
            rsp.getResult().setResultCode(FallbackResultCode.PARA_VALIDATE_ERROR.getCode());
            rsp.getResult().setResultMessage("invalid notifyType");
            break;
        }
        
        return rsp;
    }

    /**
     * 删除配置。 
     * 和fallback服务中的deleteConfig接口能力一致：当前支持通过id/serviceName/serviceName+servicePath 删除配置。
     */
    private void deleteConfig(FallbackConfig config) {
        String configId = config.getId();
        if (!StringUtils.isEmpty(configId)){
            FallbackConfigHelper.deleteById(configId);
            return ;
        }
        
        String serviceName = config.getServiceName();
        String servicePath = config.getServicePath();
        if (!StringUtils.isEmpty(serviceName) && StringUtils.isEmpty(servicePath)){
            FallbackConfigHelper.deleteByServiceName(serviceName);
            return ;
        }
        
        if (!StringUtils.isEmpty(serviceName) && !StringUtils.isEmpty(servicePath)){
            FallbackConfigHelper.deleteByServicePath(serviceName, servicePath);
            return ;
        }
    }

}
