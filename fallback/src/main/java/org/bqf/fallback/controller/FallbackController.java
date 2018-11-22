package org.bqf.fallback.controller;

import org.bqf.common.dto.inside.GetFallbackConfigReq;
import org.bqf.common.dto.inside.GetFallbackConfigRsp;
import org.bqf.fallback.dto.DeleteFallbackConfigReq;
import org.bqf.fallback.dto.DeleteFallbackConfigRsp;
import org.bqf.fallback.dto.FallbackConfigReq;
import org.bqf.fallback.dto.FallbackConfigRsp;
import org.bqf.fallback.service.IFallbackService;
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

@RestController
@RequestMapping("/fallback")
@Api(value = "降级配置接口")
public class FallbackController {

    @Autowired
    private IFallbackService fallbackService;

    @RequestMapping(value = "/addConfig", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "增加服务降级配置", notes = "FallbackConfigRspOut", response = FallbackConfigRsp.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = FallbackConfigRsp.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = FallbackConfigRsp.class) })
    public @ResponseBody FallbackConfigRsp addConfig(@RequestBody final FallbackConfigReq req) {
        return fallbackService.addConfig(req);
    }

    @RequestMapping(value = "/updateConfig", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "更新服务降级配置", notes = "FallbackConfigRspOut", response = FallbackConfigRsp.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = FallbackConfigRsp.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = FallbackConfigRsp.class) })
    public @ResponseBody FallbackConfigRsp updateConfig(@RequestBody final FallbackConfigReq req) {
        return fallbackService.updateConfig(req);
    }
    
    @RequestMapping(value = "/deleteConfig", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "更新服务降级配置", notes = "FallbackConfigRspOut", response = DeleteFallbackConfigRsp.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = DeleteFallbackConfigRsp.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = DeleteFallbackConfigRsp.class) })
    public @ResponseBody DeleteFallbackConfigRsp deleteConfig(@RequestBody final DeleteFallbackConfigReq req) {
        return fallbackService.deleteConfig(req);
    }

    @RequestMapping(value = "/getConfig", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "获取服务降级配置", notes = "FallbackConfigRspOut", response = GetFallbackConfigRsp.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = GetFallbackConfigRsp.class),
            @ApiResponse(code = 500, message = "Unexpected error", response = GetFallbackConfigRsp.class) })
    public @ResponseBody GetFallbackConfigRsp getConfig(@RequestBody final GetFallbackConfigReq req) {
        return fallbackService.getConfig(req);
    }
}
