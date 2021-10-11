package com.example.nauchki.controller;

import com.example.nauchki.model.dto.UserDto;
import com.example.nauchki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addUser(UserDto userDto) {
        return userService.saveUser(userDto) ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id, Principal principal){
        return userService.getUser(id, principal);
    }

    @GetMapping("/getuser")
    public UserDto getUserDto(Principal principal){
        return userService.getUser(principal);
    }

    @GetMapping("/test/principal")
    public String test(Principal principal) {
        return principal.toString();
    }

    @PostMapping("/del/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}