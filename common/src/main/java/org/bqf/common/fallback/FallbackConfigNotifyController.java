package org.bqf.common.fallback;

import org.bqf.common.dto.inside.FallbackConfigNotifyReq;
import org.bqf.common.dto.inside.FallbackConfigNotifyRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/common/fallback")
@Api(value = "降级配置更新时的通知接口")
public class FallbackConfigNotifyController {

    @Autowired
    private IFallbackConfigNotifyService fallbackNotifyService;

    @ApiIgnore
    @RequestMapping(value = "/notify", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "服务降级配置更新后通知业务侧", notes = "FallbackConfigRspOut", response = FallbackConfigNotifyRsp.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = FallbackConfigNotifyRsp.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = FallbackConfigNotifyRsp.class) })
    public @ResponseBody FallbackConfigNotifyRsp notify(@RequestBody final FallbackConfigNotifyReq req) {
        return fallbackNotifyService.notify(req);
    }
}
