package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianp
 **/
@SpringBootApplication
@RestController
@RequestMapping("/")
public class Application {
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("testDymicData")
    public String testDymicData() {
        String name = context.getEnvironment().getProperty("name");
        System.out.println(name);
        return name;
    }
}
