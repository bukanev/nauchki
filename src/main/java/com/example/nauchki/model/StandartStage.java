package com.example.nauchki.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "standart_stage")
public class StandartStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "days", nullable = false)
    private Integer days;
    private String gender;
    private Float medianHeightMinus3;
    private Float medianHeightMinus2;
    private Float medianHeightMinus1;
    private Float medianHeight;
    private Float medianHeightPlus1;
    private Float medianHeightPlus2;
    private Float medianHeightPlus3;

    private Float medianWeightMinus3;
    private Float medianWeightMinus2;
    private Float medianWeightMinus1;
    private Float medianWeight;
    private Float medianWeightPlus1;
    private Float medianWeightPlus2;
    private Float medianWeightPlus3;

    @Column(length = 4096)
    private String skills;

    private String alternateText;

    public StandartStage(Long id, Integer days, String gender,
                         String medianHeightMinus3,
                         String medianHeightMinus2,
                         String medianHeightMinus1,
                         String medianHeight,
                         String medianHeightPlus1,
                         String medianHeightPlus2,
                         String medianHeightPlus3,
                         String medianWeightMinus3,
                         String medianWeightMinus2,
                         String medianWeightMinus1,
                         String medianWeight,
                         String medianWeightPlus1,
                         String medianWeightPlus2,
                         String medianWeightPlus3,
                         String skills) {
        this.id = id;
        this.days = days;
        this.gender = gender;
        this.medianHeightMinus3 = Float.parseFloat(medianHeightMinus3.replace(",","."));
        this.medianHeightMinus2 = Float.parseFloat(medianHeightMinus2.replace(",","."));
        this.medianHeightMinus1 = Float.parseFloat( medianHeightMinus1.replace(",","."));
        this.medianHeight = Float.parseFloat(medianHeight.replace(",","."));
        this.medianHeightPlus1 = Float.parseFloat(medianHeightPlus1.replace(",","."));
        this.medianHeightPlus2 = Float.parseFloat(medianHeightPlus2.replace(",","."));
        this.medianHeightPlus3 = Float.parseFloat(medianHeightPlus3.replace(",","."));
        this.medianWeightMinus3 = Float.parseFloat(medianWeightMinus3.replace(",","."));
        this.medianWeightMinus2 = Float.parseFloat(medianWeightMinus2.replace(",","."));
        this.medianWeightMinus1 =Float.parseFloat( medianWeightMinus1.replace(",","."));
        this.medianWeight = Float.parseFloat(medianWeight.replace(",","."));
        this.medianWeightPlus1 = Float.parseFloat(medianWeightPlus1.replace(",","."));
        this.medianWeightPlus2 = Float.parseFloat(medianWeightPlus2.replace(",","."));
        this.medianWeightPlus3 =Float.parseFloat( medianWeightPlus3.replace(",","."));
        this.skills = skills;
    }

}
