package com.example.nauchki.controller;

import com.example.nauchki.forms.StandartStageForm;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@ApiIgnore
public class AdminController {
    private final StageService stageService;

    @GetMapping
    public String getAdminPage(Model model){
        model.addAttribute("stages",stageService.getAllStandartStage());
        return "admin";
    }

    @GetMapping("/stage/{id}")
    public String getStStage(Model model, @PathVariable("id") Long id){
        model.addAttribute("stage", stageService.getStage(id));
        return "stageedit";
    }
    @PostMapping("/stage/{id}")
    public String editStandartStage(StandartStageForm stage, @PathVariable("id") Long id){
        System.out.println(stage.getId() + " :: "+ id);
        return stageService.editStandartStage(stage.mapToStStage()) ?
                "redirect:/admin" :
                String.valueOf(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/st")
    public ResponseEntity<ResponseStatus> saveStandartStage(StandartStageForm stage){
        System.out.println("saveStStage");
        stage.setId(999L);
        return stageService.saveStandartStage(stage.mapToStStage(), "admin")?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/st")
    public String addStagePage(){
        return "addstage";
    }
}
