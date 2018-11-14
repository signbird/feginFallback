package org.bqf.content.controller;

import org.bqf.content.dto.GetContentReq;
import org.bqf.content.dto.GetContentRsp;
import org.bqf.content.dto.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contentCenter")
public class ContentQueryController {

    @RequestMapping(value = "/getContent", produces = { "application/json" }, method = RequestMethod.POST)
    public GetContentRsp getContent(@RequestBody final GetContentReq req) {
        
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(req);
        return new GetContentRsp(new Result("1000000000", "Success"), "content from content-query");
    }
    
}
