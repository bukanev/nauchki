package com.example.nauchki.controller;

import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import com.example.nauchki.service.StageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
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


    @ApiOperation("Добавление описания стандартного состояния ребенка в справочник Standart Stage")
    @PostMapping("/st/{name}")
    public ResponseEntity<ResponseStatus> saveStandartStage(
            @RequestBody StandartStage stage,
            @PathVariable @Parameter(description = "Имя пользователя (должен быть admin)", required = true) String name){
        System.out.println("saveStStage");
        return stageService.saveStandartStage(stage,name)?
               new ResponseEntity<>(HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Редактирование описания стандартного состояния ребенка в справочник Standart Stage")
    @PostMapping("/stedit/{name}")
    public ResponseEntity<ResponseStatus> editStandartStage(
            @RequestBody StandartStage stage,
            @PathVariable @Parameter(description = "Имя пользователя (должен быть admin)", required = true) String name){
        return stageService.editStandartStage(stage, name)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Добавление описания состояния ребенка пользователем")
    @PostMapping("/us/{id}")
    public ResponseEntity<ResponseStatus> saveUserStage(
            @PathVariable @Parameter(description = "Идентификатор ребенка", required = true) Long id,
            @RequestBody UserStage stage,
            @RequestHeader("Authorization") String token){
        return stageService.saveUserStage(id,stage,token)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Получение списка описаний всех стандартных состояний ребенка из справочника Standart Stage")
    @GetMapping("/getallstage")
    public List<StandartStage> getStageList(){
        return stageService.getAllStandartStage();
    }

}
