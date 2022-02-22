package com.example.nauchki.controller;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.dto.ChildrenDto;
import com.example.nauchki.repository.StandartStageRepo;
import com.example.nauchki.service.ChildrenService;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/children/{id}")
    public ResponseEntity<ResponseStatus> addChildren(@PathVariable Long id,@RequestBody Children children) {
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
    public List<ChildrenDto> getChildren(@PathVariable Long id) {
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
            @RequestParam("file") MultipartFile file,
            @PathVariable Long id,
            Principal principal){
        return childrenService.addChildrenImg(file, id, principal);
    }

    @ApiOperation("Добавление фотки в альбом ребенка")
    @PostMapping("/addchildrengallery/{id}")
    public String addChildrenImgToGallery(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long id,
            @RequestParam String comment,
            Principal principal){
        return childrenService.addChildrenImgToList(file, id, comment, principal);
    }
}
