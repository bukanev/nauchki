package com.example.nauchki.controller;

import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.UserDto;
import com.example.nauchki.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping()
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("Регистрация нового пользователя")
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto) ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping("/getuser")
    public ResponseEntity<HttpStatus> getUserDto(Principal principal){
        if(principal != null){
            System.out.println("============ "+principal.toString());
            return new ResponseEntity(userService.getUser(principal.getName()), HttpStatus.OK);
        }
        return new ResponseEntity(new UserDto(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user")
    public ResponseEntity<HttpStatus> getUserDtoByName(@RequestBody UserDto userDto){
        if(userDto.getEmail()!= null){
            return new ResponseEntity(userService.getUser(userDto.getEmail()), HttpStatus.OK);
        }
        return new ResponseEntity(new UserDto(), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("ADMIN")
    @PostMapping("/del/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Изменение пароля принимает почту")
    @PostMapping("/editpassword")
    public ResponseEntity<HttpStatus> editPassword(@RequestBody UserDto userDto){
        return userService.editPassword(userDto) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    /*@ApiOperation("Добавление картинку пользователя по его id")
    @PostMapping("/addimage/{id}")
    public String addImage(@RequestParam("file") MultipartFile file,@PathVariable Long id ){
        return userService.addImage(file, id);
    }*/

    @ApiOperation("Добавление картинку пользователя по его Principal")
    @PostMapping("/addimg")
    public String addImg(@RequestParam("file") MultipartFile file,Principal principal){
        return userService.addImage(file, principal);
    }

    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity auth(@RequestBody User user) {
        return userService.getAuthEmail(user.getEmail(), user.getPassword());
    }
}