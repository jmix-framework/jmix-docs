package com.company.demo.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("endpoints-security")
//tag::whole-class[]
@RestController
@RequestMapping("/api")
public class BasicGreetingController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/public/hi")
    public String publicHi() {
        return "Hi!";
    }
}
//end::whole-class[]