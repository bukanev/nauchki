package com.example.nauchki.model.dto;


import com.example.nauchki.model.Children;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ChildrenDto {

    private Long id;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String timeOfBirth;
    private List<StandartStage> standartStages;
    private List<UserStage> userStages;
    private String img_path;

    public ChildrenDto(Long id, String name, String gender, String dateOfBirth, String timeOfBirth,
                       List<StandartStage> standartStages, List<UserStage> userStages, String img_path) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.timeOfBirth = timeOfBirth;
        this.standartStages = standartStages;
        this.userStages = userStages;
        this.img_path = img_path;
    }

    public static ChildrenDto valueOf(Children children) {
        return new ChildrenDto(
                children.getId(),
                children.getName(),
                children.getGender(),
                children.getDateOfBirth(),
                children.getTimeOfBirth(),
                children.getStandartStages(),
                children.getUserStages(),
                children.getImg_path()
                );
    }

    public Children mapToChildren(){
        Children children = new Children();
        children.setName(name);
        children.setGender(gender);
        children.setDateOfBirth(dateOfBirth);
        children.setTimeOfBirth(timeOfBirth);
        children.setStandartStages(standartStages);
        children.setUserStages(userStages);
        children.setImg_path(img_path);
        return children;
    }

    public void addStStage(StandartStage stage){
        if(standartStages == null){
            standartStages = new ArrayList<>();
        }
        standartStages.add(stage);
    }
}
