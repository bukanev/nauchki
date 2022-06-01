package com.example.nauchki.controller;

import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.UserDto;
import com.example.nauchki.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @ApiOperation("Регистрация нового пользователя")
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto) ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("Получение пользователя по id")
    @GetMapping("/user/{id}")
    public UserDto getUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) Long id){
        return userService.getUser(id);
    }

    @ApiOperation("Получение текущего пользователя")
    @GetMapping("/getuser")
    public ResponseEntity<HttpStatus> getUserDto(Principal principal){
        if(principal != null){
            return new ResponseEntity(userService.getUser(principal.getName()), HttpStatus.OK);
        }
        return new ResponseEntity(new UserDto(), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("Получение пользователя по Email")
    @PostMapping("/user")
    public ResponseEntity<HttpStatus> getUserDtoByName(@RequestBody UserDto userDto){
        if(userDto.getEmail()!= null){
            return new ResponseEntity(userService.getUser(userDto.getEmail()), HttpStatus.OK);
        }
        return new ResponseEntity(new UserDto(), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("Удаление пользователя")
    @PreAuthorize("ADMIN")
    @PostMapping("/del/{id}")
    public ResponseEntity<HttpStatus> deleteUser(
            @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) Long id){
        return userService.deleteUser(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Изменение пароля принимает почту в JSON")
    @PostMapping("/editpassword")
    public ResponseEntity<HttpStatus> editPassword(@RequestBody UserDto userDto){
        return userService.editPassword(userDto) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    /**
     * Обязательно принимается password и activationCode
     * @param userDto
     * @return
     */
    @ApiOperation("Изменение пароля принимает пароль и код в JSON")
    @PostMapping("/editpass")
    public ResponseEntity<HttpStatus> editPass(@RequestBody UserDto userDto){
        return userService.editPass(userDto) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Авторизация")
    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity auth(@RequestBody User user) {
        return userService.getAuthEmail(user.getEmail(), user.getPassword());
    }

    @ApiOperation("Добавление картинки пользователя по его Principal")
    @PostMapping("/addimg")
    public String addImg(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name="tags", required = false) @Parameter(description = "Тэг картинки пользователя") String tags,
            @RequestParam(name="description", required = false) @Parameter(description = "Описание картинки пользователя") String description,
            Principal principal){
        return userService.addImage(file, principal, tags, description);
    }

    @ApiOperation("Делает выбранную по id картинку пользователя основной")
    @PutMapping("/setbaseimage/{id}")
    public Long setBaseImg(
            @PathVariable(name = "id") @Parameter(description = "Идентификатор пользователя", required = true) Long imgId,
            @RequestParam("file") MultipartFile file,
            Principal principal){
        return userService.setBaseImage(imgId, principal);
    }

    @ApiOperation("Удаление выбранной по id картинки пользователя по его Principal," +
            " или основной картинки, если id не указан")
    @DeleteMapping("/deleteimg/{id}")
    public ResponseEntity<HttpStatus> deleteImg(
            Principal principal,
            @PathVariable(name = "id", required = false) @Parameter(description = "Идентификатор картинки пользователя", required = true) Long imgId){
        if(imgId!=null && imgId>0){
            return userService.deleteImg(principal, imgId) ?
                    new ResponseEntity<>(HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }else{
            return userService.deleteImg(principal) ?
                    new ResponseEntity<>(HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

}