package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {
    @GetMapping("afficher")
    public String user(){
        return "user";
    }
    public ResponseEntity<Map<String,Object>> getUser(){
 return null ;
    }
}
