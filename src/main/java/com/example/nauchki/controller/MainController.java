package com.example.nauchki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MainController {

   /* @GetMapping(value = "/reg")
    public String reg(){
        return "registrate.html";
    }*/

    @GetMapping()
    public String hello(){
        return "Hello world!";
    }
}
