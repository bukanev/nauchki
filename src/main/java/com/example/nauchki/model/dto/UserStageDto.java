package com.example.nauchki.model.dto;

import com.example.nauchki.model.Comment;
import com.example.nauchki.model.UserStage;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserStageDto {
    private Long id;
    private Integer days;
    private String stageDate;
    private Integer height;
    private Integer weight;
    private String skills;
    private Set<Comment> commentSet;

    public UserStageDto(Long id, Integer days, String stageDate, Integer height, Integer weight, String skills, Set<Comment> comments) {
        this.id = id;
        this.days = days;
        this.stageDate = stageDate;
        this.height = height;
        this.weight = weight;
        this.skills = skills;
        this.commentSet = comments;
    }

    public static UserStageDto valueOf(UserStage stage){
        return new UserStageDto(
                stage.getId(),
                stage.getDays(),
                stage.getStageDate(),
                stage.getHeight(),
                stage.getWeight(),
                stage.getSkills(),
                stage.getComments()
        );
    }

    public UserStage mapToUserStage(){
        UserStage stage = new UserStage();
        stage.setId(id);
        stage.setDays(days);
        stage.setHeight(height);
        stage.setWeight(weight);
        stage.setSkills(skills);
        stage.setComments(commentSet);
        return stage;
    }
}
