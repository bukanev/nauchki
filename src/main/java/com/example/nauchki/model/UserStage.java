package com.example.nauchki.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "user_stage")
@Schema(description = "Описание этапа жизни ребенка родителем")
public class UserStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID записи", required = true, example = "1")
    private Long id;
    @Schema(description = "Количество дней от рождения ребенка", example = "14")
    private Integer days;
    @Schema(description = "Дата описания", example = "15.01.2015")
    private String stageDate;
    @Schema(description = "Рост", example = "55.2")
    private Integer height;
    @Schema(description = "Вес", example = "36.6")
    private Integer weight;
    @Column(length = 2048)
    @Schema(description = "Особенности поведение ребенка, достижения", example = "Повысилось беспокойство, стал внимательней к речи")
    private String skills;

    @OneToMany(mappedBy = "userstage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comment> comments;


    public void setDays(Integer days) {
        if(days < 0){
            this.days = 0;
        }else{
            this.days = days;
        }
    }

    public void setStageDate(String stagedate) {
        this.stageDate = stagedate;
    }

    public void setHeight(Integer height) {
        if(days < 0){
            this.height = 0;
        }else{
            this.height = height;
        }
    }

    public void setWeight(Integer weight) {
        if(days < 0){
            this.weight = 0;
        }else{
            this.weight = weight;
        }
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "UserStage{" +
                "id=" + id +
                ", days=" + days +
                ", stageDate='" + stageDate + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", skills='" + skills + '\'' +
                '}';
    }
}
