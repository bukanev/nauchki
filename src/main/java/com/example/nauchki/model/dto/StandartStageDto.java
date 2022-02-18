package com.example.nauchki.model.dto;

import com.example.nauchki.model.StandartStage;

public class StandartStageDto {
    private Long id;
    private int days;
    private String skills;
    private float medianHeightMinus3;
    private float medianHeightMinus2;
    private float medianHeightMinus1;
    private float medianHeight;
    private float medianHeightPlus1;
    private float medianHeightPlus2;
    private float medianHeightPlus3;

    private float medianWeightMinus3;
    private float medianWeightMinus2;
    private float medianWeightMinus1;
    private float medianWeight;
    private float medianWeightPlus1;
    private float medianWeightPlus2;
    private float medianWeightPlus3;

    public StandartStageDto(Long id, int days, String skills, float medianHeightMinus3, float medianHeightMinus2, float medianHeightMinus1, float medianHeight, float medianHeightPlus1, float medianHeightPlus2, float medianHeightPlus3, float medianWeightMinus3, float medianWeightMinus2, float medianWeightMinus1, float medianWeight, float medianWeightPlus1, float medianWeightPlus2, float medianWeightPlus3) {
        this.id = id;
        this.days = days;
        this.skills = skills;
        this.medianHeightMinus3 = medianHeightMinus3;
        this.medianHeightMinus2 = medianHeightMinus2;
        this.medianHeightMinus1 = medianHeightMinus1;
        this.medianHeight = medianHeight;
        this.medianHeightPlus1 = medianHeightPlus1;
        this.medianHeightPlus2 = medianHeightPlus2;
        this.medianHeightPlus3 = medianHeightPlus3;
        this.medianWeightMinus3 = medianWeightMinus3;
        this.medianWeightMinus2 = medianWeightMinus2;
        this.medianWeightMinus1 = medianWeightMinus1;
        this.medianWeight = medianWeight;
        this.medianWeightPlus1 = medianWeightPlus1;
        this.medianWeightPlus2 = medianWeightPlus2;
        this.medianWeightPlus3 = medianWeightPlus3;
    }

    public static StandartStageDto valueOf(StandartStage stage){
        return new StandartStageDto(
                stage.getId(),
                stage.getDays(),
                stage.getSkills(),
                stage.getMedianHeightMinus3(),
                stage.getMedianHeightMinus2(),
                stage.getMedianHeightMinus1(),
                stage.getMedianHeight(),
                stage.getMedianHeightPlus1(),
                stage.getMedianHeightPlus2(),
                stage.getMedianHeightPlus3(),
                stage.getMedianWeightMinus3(),
                stage.getMedianWeightMinus2(),
                stage.getMedianWeightMinus1(),
                stage.getMedianWeight(),
                stage.getMedianWeightPlus1(),
                stage.getMedianWeightPlus2(),
                stage.getMedianWeightPlus3()
        );
    }
}
