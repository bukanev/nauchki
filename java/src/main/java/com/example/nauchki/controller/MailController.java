package com.example.nauchki.controller;

import com.example.nauchki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {
    @Autowired
    private UserService userService;

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        if(isActivated) {
            return "User successfully activated!" ;
        }
        return "Activation code is not found!";
    }

}
