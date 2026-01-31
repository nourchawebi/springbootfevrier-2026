package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("test")
@RestController
public class testController {
    @GetMapping("afficher")
    public String test(){
        return "test";
    }
}
