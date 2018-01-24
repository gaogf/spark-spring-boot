package com.learn.controller;

import com.learn.service.SparkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pactera on 2018/1/23.
 */
@RestController
public class HelloController {
    @Autowired
    private SparkService sparkService;

    @RequestMapping("/hello")
    public String index(){
        return "Hello World";
    }
    @RequestMapping("/spark")
    public String getSpark(){
        return sparkService.getVersion();
    }
}
