package com.example.nauchki.controller;

import com.example.nauchki.exceptions.OtherUserDataExeption;
import com.example.nauchki.jwt.JwtProvider;
import com.example.nauchki.model.Children;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.dto.ChildrenDto;
import com.example.nauchki.repository.StandartStageRepo;
import com.example.nauchki.service.ChildrenService;
import com.example.nauchki.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChildrenController {
    private final ChildrenService childrenService;
    private final StandartStageRepo stageRepo;
    private final JwtProvider jwtProvider;
    private final UserService userService;


    @ApiOperation("Добавление ребенка по id родителя")
    @PostMapping("/children/{id}")
    public ResponseEntity<ResponseStatus> addChildren(
            @PathVariable @Parameter(description = "Идентификатор родителя ребенка", required = true) Long id,
            @RequestBody Children children,
            @RequestHeader("Authorization") String token) {
        isCorrectParent(id, token);
        return childrenService.addChildren(id, children) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    /**
     * ПОлучение детей по id родителя
     * @param id родителя
     * @return Лист с детьми.
     */
    @ApiOperation("Получение детей по id родителя")
    @GetMapping("/getchildren/{id}")
    public List<ChildrenDto> getChildren(
            @PathVariable @Parameter(description = "Идентификатор родителя ребенка", required = true) Long id,
            @RequestHeader("Authorization") String token) {
        isCorrectParent(id, token);
        return childrenService.getChildren(id);
    }

    /*@GetMapping("/getstage/{id}")
    public List<StandartStage> getStage(@PathVariable int id) {
        return stageRepo.findByDay(id);
    }*/

    @ApiOperation("Изменение данных ребенка")
    @PostMapping("/children")
    public ResponseEntity<ResponseStatus> editChildren(@RequestBody Children children) {
        return childrenService.editChildren(children) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Удаление ребенка")
    @DeleteMapping("/deletechildren")
    public ResponseEntity<ResponseStatus> deleteChildren(@RequestBody Children children) {
        return childrenService.deleteChildren(children) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Добавление фотки ребенка")
    @PostMapping("/addchildrenimg/{id}")
    public String addChildrenImg(
            @RequestParam("file") @Parameter(description = "Файл с изображением ребенка", required = true) MultipartFile file,
            @PathVariable @Parameter(description = "Идентификатор ребенка", required = true) Long id,
            @RequestHeader("Authorization") String token,
            Principal principal){
        isCorrectParent(childrenService.getParentId(id), token);
        return childrenService.addChildrenImg(file, id, principal);
    }

    @ApiOperation("Добавление фотки в альбом ребенка")
    @PostMapping("/addchildrengallery/{id}")
    public String addChildrenImgToGallery(
            @RequestParam("file") @Parameter(description = "Файл с изображением ребенка", required = true) MultipartFile file,
            @PathVariable @Parameter(description = "Идентификатор ребенка", required = true) Long id,
            @RequestParam @Parameter(description = "Комментарий") String comment,
            @RequestHeader("Authorization") String token,
            Principal principal){
        isCorrectParent(childrenService.getParentId(id), token);
        return childrenService.addChildrenImgToList(file, id, comment, principal);
    }

    private void isCorrectParent(Long id, String token) {
        String userAuthEmail = jwtProvider.getUsername(token.substring(7));
        String userGetEmail = userService.getEmail(id);
        if (!userAuthEmail.equals(userGetEmail)) {
            throw new OtherUserDataExeption("Это не ваш ребенок!");
        }
    }

}
