package com.wangbz.examples.sn.controller;

import com.wangbz.examples.sn.core.SequenceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {
    @Resource
    private SequenceService service;

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam("type") String type, @RequestParam("company") String company) {
        for (int i = 0; i < 10; i++) {
            final String itype = type + i + "_";
            new Thread(() -> {
                for (int j = 0; j < 1010; j++) {
                    System.out.println(service.generateOrderNo(itype, company));
                }
            }).start();
        }
        return "ok";
    }
}
