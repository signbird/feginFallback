package org.bqf.content.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {

    
    @RequestMapping(value = "/test", produces = { "application/json" }, method = RequestMethod.POST)
    public @ResponseBody String queryContent(@RequestBody final String req) {
        return "success";
    }
}
