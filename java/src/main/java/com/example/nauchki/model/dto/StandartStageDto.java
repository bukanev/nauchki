package com.example.nauchki.model.dto;

import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;

public class StandartStageDto {
    private Long id;
    private Integer days;
    private Integer heightWHO;
    private Integer heightUSSR;
    private Integer weightWHO;
    private Integer weightUSSR;
    private String skills;

    public StandartStageDto(Long id, Integer days, Integer heightWHO, Integer heightUSSR, Integer weightWHO, Integer weightUSSR, String skills) {
        this.id = id;
        this.days = days;
        this.heightWHO = heightWHO;
        this.heightUSSR = heightUSSR;
        this.weightWHO = weightWHO;
        this.weightUSSR = weightUSSR;
        this.skills = skills;
    }

    public static StandartStageDto valueOf(StandartStage stage){
        return new StandartStageDto(
                stage.getId(),
                stage.getDays(),
                stage.getHeightWHO(),
                stage.getHeightUSSR(),
                stage.getWeightWHO(),
                stage.getWeightUSSR(),
                stage.getSkills()
        );
    }

    public StandartStage mapToStandartStage(){
        StandartStage stage = new StandartStage();
        stage.setId(id);
        stage.setDays(days);
        stage.setHeightWHO(heightWHO);
        stage.setHeightUSSR(heightUSSR);
        stage.setWeightWHO(weightWHO);
        stage.setWeightUSSR(weightUSSR);
        stage.setSkills(skills);
        return stage;
    }
}
