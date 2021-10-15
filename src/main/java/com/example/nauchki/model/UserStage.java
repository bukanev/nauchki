package com.example.nauchki.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "user_stage")
public class UserStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer days;

    /** Добавить паттерн проверки даты в формате дд-ММ-гггг */
    @Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])[- ](0?[1-9]|1[012])[-](19|20)?[0-9]{2}$")
    private String stageDate;
    private Integer height;
    private Integer weight;
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
