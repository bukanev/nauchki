package com.example.nauchki.controller;

import com.example.nauchki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
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


    @PostMapping("/editpass/{code}")
    public ResponseEntity<HttpStatus> editPass(@PathVariable String code, String password){
        System.out.println(password);
        return userService.editPass(code, password) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
