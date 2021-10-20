package com.example.nauchki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
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
