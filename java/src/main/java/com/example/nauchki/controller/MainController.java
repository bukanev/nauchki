package com.example.nauchki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequiredArgsConstructor
public class MainController {

   /* @GetMapping(value = "/reg")
    public String reg(){
        return "registrate.html";
    }*/

    @GetMapping()
    public String hello(){
        return "Hello";
    }
}
