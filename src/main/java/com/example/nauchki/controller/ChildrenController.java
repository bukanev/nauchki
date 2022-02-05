package com.example.nauchki.controller;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.dto.ChildrenDto;
import com.example.nauchki.repository.StandartStageRepo;
import com.example.nauchki.service.ChildrenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/getchildren/{id}")
    public List<ChildrenDto> getChildren(@PathVariable Long id) {
        return childrenService.getChildren(id);
    }

    @GetMapping("/getstage/{id}")
    public List<StandartStage> getStage(@PathVariable int id) {
        return stageRepo.findByDay(id);
    }

    @PostMapping("/children")
    public ResponseEntity<ResponseStatus> editChildren(@RequestBody Children children) {
        return childrenService.editChildren(children) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/deletechildren")
    public ResponseEntity<ResponseStatus> deleteChildren(@RequestBody Children children) {
        return childrenService.deleteChildren(children) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/addchildrenimg/{id}")
    public String addChildrenImg(@RequestParam("file") MultipartFile file, @PathVariable Long id ){
        return childrenService.addChildrenImg(file, id);
    }

    @PostMapping("/addchildrengallery/{id}")
    public String addChildrenImgToGallery(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long id,
            @RequestParam String comment){
        return childrenService.addChildrenImgToList(file, id, comment);
    }
}
