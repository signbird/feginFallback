package org.bqf.test.controller;

import org.bqf.test.dto.outside.GetPlayDownloadUrlReqOut;
import org.bqf.test.dto.outside.GetPlayDownloadUrlRspOut;
import org.bqf.test.dto.outside.QueryContentReqOut;
import org.bqf.test.dto.outside.QueryContentRspOut;
import org.bqf.test.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoon")
public class ContentController {

    @Autowired
    private IContentService contentService;
    
    @RequestMapping(value = "/queryContent", produces = { "application/json" }, method = RequestMethod.POST)
    public @ResponseBody QueryContentRspOut queryContent(@RequestBody final QueryContentReqOut req) {
        return contentService.queryContent(req);
    }
    
    
    @RequestMapping(value = "/getPlayDownloadUrl", produces = { "application/json" }, method = RequestMethod.POST)
    public @ResponseBody GetPlayDownloadUrlRspOut getPlayDownloadUrl(@RequestBody final GetPlayDownloadUrlReqOut req) {
        return contentService.getPlayDownloadUrl(req);
    }
}
