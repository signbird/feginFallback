package org.bqf.service.controller;

import org.bqf.common.dto.outside.GetPlayDownloadUrlReqOut;
import org.bqf.common.dto.outside.GetPlayDownloadUrlRspOut;
import org.bqf.common.dto.outside.QueryContentReqOut;
import org.bqf.common.dto.outside.QueryContentRspOut;
import org.bqf.service.service.IContentService;
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
@RequestMapping("/cartoon")
@Api(value = "内容相关接口", description = "内容API")
public class ContentController {

    @Autowired
    private IContentService contentService;
    
    @RequestMapping(value = "/queryContent", produces = { "application/json" }, method = RequestMethod.POST)
    @ApiOperation(value = "查询内容", notes = "查询内容", response = QueryContentRspOut.class, tags = {
    "content" })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "返回", response = QueryContentRspOut.class),
    @ApiResponse(code = 500, message = "Unexpected error", response = QueryContentRspOut.class) })
    public @ResponseBody QueryContentRspOut queryContent(@RequestBody final QueryContentReqOut req) {
        return contentService.queryContent(req);
    }
    
    
    @RequestMapping(value = "/getPlayDownloadUrl", produces = { "application/json" }, method = RequestMethod.POST)
    public @ResponseBody GetPlayDownloadUrlRspOut getPlayDownloadUrl(@RequestBody final GetPlayDownloadUrlReqOut req) {
        return contentService.getPlayDownloadUrl(req);
    }
}
