package com.example.nauchki.controller;

import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import com.example.nauchki.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/stage")
@RestController
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;


    @PostMapping("/st")
    public ResponseEntity<ResponseStatus> saveStandartStage(@RequestBody StandartStage stage, Principal principal){
       return stageService.saveStandartStage(stage, principal)?
               new ResponseEntity<>(HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/stedit")
    public ResponseEntity<ResponseStatus> editStandartStage(@RequestBody StandartStage stage, Principal principal){
        return stageService.editStandartStage(stage, principal)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/us")
    public ResponseEntity<ResponseStatus> saveUserStage(@RequestBody UserStage stage){
        return stageService.saveUserStage(stage)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}