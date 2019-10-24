package com.mic.config.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianp
 **/
@RestController
@RequestMapping("/")
public class TestController {

    @Value("${abc}")
    private String str;

    @GetMapping("/")
    public String testGetValue() {
        System.out.println(str);
        return str;
    }
}
