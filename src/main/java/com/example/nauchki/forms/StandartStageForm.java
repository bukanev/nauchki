package com.example.nauchki.forms;

import com.example.nauchki.model.StandartStage;
import lombok.Data;

@Data
public class StandartStageForm {
    private Long id;
    private Integer days;
    private String gender;
    private String medianHeightMinus3;
    private String medianHeightMinus2;
    private String medianHeightMinus1;
    private String medianHeight;
    private String medianHeightPlus1;
    private String medianHeightPlus2;
    private String medianHeightPlus3;

    private String medianWeightMinus3;
    private String medianWeightMinus2;
    private String medianWeightMinus1;
    private String medianWeight;
    private String medianWeightPlus1;
    private String medianWeightPlus2;
    private String medianWeightPlus3;
    private String skills;


    public StandartStage mapToStStage(){
        return new StandartStage(
                id,
                days,
                gender,
                medianHeightMinus3,
                medianHeightMinus2,
                medianHeightMinus1,
                medianHeight,
                medianHeightPlus1,
                medianHeightPlus2,
                medianHeightPlus3,
                medianWeightMinus3,
                medianWeightMinus2,
                medianWeightMinus1,
                medianWeight,
                medianWeightPlus1,
                medianWeightPlus2,
                medianWeightPlus3,
                skills
        );
    }
}
