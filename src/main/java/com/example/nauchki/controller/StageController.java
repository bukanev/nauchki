package com.example.nauchki.controller;

import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import com.example.nauchki.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/stage")
@RestController
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;


    @PostMapping("/st/{name}")
    public ResponseEntity<ResponseStatus> saveStandartStage(@RequestBody StandartStage stage, @PathVariable String name){
        System.out.println("saveStStage");
        return stageService.saveStandartStage(stage,name)?
               new ResponseEntity<>(HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/stedit/{name}")
    public ResponseEntity<ResponseStatus> editStandartStage(@RequestBody StandartStage stage, @PathVariable String name){
        return stageService.editStandartStage(stage, name)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/us/{id}")
    public ResponseEntity<ResponseStatus> saveUserStage(@PathVariable Long id, @RequestBody UserStage stage){
        return stageService.saveUserStage(id,stage)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/getallstage")
    public List<StandartStage> getStageList(){
        return stageService.getAllStandartStage();
    }

}
