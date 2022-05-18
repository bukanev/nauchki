package com.example.nauchki.controller;

import com.example.nauchki.service.FileService;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@ApiIgnore
@Controller
@RequiredArgsConstructor
public class MainController {
    private final FileService fileService;

    @GetMapping()
    public String hello(){
        return "Hel+  lo";
    }


    @GetMapping("/editpassword/{code}")
    public String editPasswordPage(@PathVariable String code){
        return "editpassword";
    }
}
