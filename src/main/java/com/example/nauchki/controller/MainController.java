package com.example.nauchki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@ApiIgnore
@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping()
    public String hello(){
        return "Hello";
    }


    @GetMapping("/editpassword/{code}")
    public String editPasswordPage(@PathVariable String code, Model model){
        model.addAttribute("code", code);
        return "editpassword";
    }
}
