package com.company.demo.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("endpoints-security")
//tag::whole-class[]
@RestController
public class GreetingController {

    @PostMapping("/greeting/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/greeting/public/hi")
    public String hi() {
        return "Hi!";
    }
}
//end::whole-class[]