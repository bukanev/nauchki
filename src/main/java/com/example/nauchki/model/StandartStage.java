package com.example.nauchki.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "standart_stage")
@Schema(description = "Описание этапа жизни ребенка стандартное (справочное)")
public class StandartStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID записи", required = true, example = "17")
    private Long id;
    @Column(name = "days", nullable = false)
    @Schema(description = "Количество дней от рождения ребенка", example = "14")
    private Integer days;
    @Schema(description = "Пол ребенка", example = "Жен")
    private String gender;
    @Schema(description = "Отклонение среднего роста ребенка в сторону уменьшения №3", example = "49.6")
    private Float medianHeightMinus3;
    @Schema(description = "Отклонение среднего роста ребенка в сторону уменьшения №2", example = "47.7")
    private Float medianHeightMinus2;
    @Schema(description = "Отклонение среднего роста ребенка в сторону уменьшения №1", example = "45.8")
    private Float medianHeightMinus1;
    @Schema(description = "Средний рост ребенка", example = "51.5")
    private Float medianHeight;
    @Schema(description = "Отклонение среднего роста ребенка в сторону увеличения №1", example = "53.4")
    private Float medianHeightPlus1;
    @Schema(description = "Отклонение среднего роста ребенка в сторону увеличения №2", example = "55.3")
    private Float medianHeightPlus2;
    @Schema(description = "Отклонение среднего роста ребенка в сторону увеличения №3", example = "57.2")
    private Float medianHeightPlus3;

    @Schema(description = "Отклонение среднего веса ребенка в сторону уменьшения №3", example = "2.3")
    private Float medianWeightMinus3;
    @Schema(description = "Отклонение среднего веса ребенка в сторону уменьшения №2", example = "2.7")
    private Float medianWeightMinus2;
    @Schema(description = "Отклонение среднего веса ребенка в сторону уменьшения №1", example = "3.1")
    private Float medianWeightMinus1;
    @Schema(description = "Средний вес ребенка", example = "3.6")
    private Float medianWeight;
    @Schema(description = "Отклонение среднего веса ребенка в сторону увеличения №1", example = "4.1")
    private Float medianWeightPlus1;
    @Schema(description = "Отклонение среднего веса ребенка в сторону увеличения №2", example = "4.7")
    private Float medianWeightPlus2;
    @Schema(description = "Отклонение среднего веса ребенка в сторону увеличения №3", example = "5.4")
    private Float medianWeightPlus3;

    @Column(length = 4096)
    @Schema(description = "Достижения, которые прпоявляются у ребенка в этом возрасте", example = "У ребенка начинает сильнее проявляться реакция на резкий звук. Может прекратить движение, прием пищи, замереть.")
    private String skills;

    @Schema(description = "Комментарий", example = "")
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
