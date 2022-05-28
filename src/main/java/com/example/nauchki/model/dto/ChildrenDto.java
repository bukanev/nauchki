package com.example.nauchki.model.dto;


import com.example.nauchki.model.Children;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Schema(description = "Модель данных о ребенке")
public class ChildrenDto {

    @Schema(description = "ID ребенка", required = true, example = "1")
    private Long id;
    @Schema(description = "Имя ребенка", example = "Лена")
    private String name;
    @Schema(description = "Пол ребенка", example = "Жен")
    private String gender;
    @Schema(description = "Дата рождения ребенка", example = "01.01.2015")
    private String dateOfBirth;
    @Schema(description = "Время рождения ребенка", example = "13:23:45")
    private String timeOfBirth;
    private List<StandartStage> standartStages;
    private List<UserStage> userStages;
    @Schema(description = "Путь к изображению", example = "/img/img.png")
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
