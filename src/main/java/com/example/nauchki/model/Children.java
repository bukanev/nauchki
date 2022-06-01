package com.example.nauchki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "children")
@Schema(description = "Модель ребенка")
public class Children {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID ребенка", required = true, example = "1")
    private Long id;
    @Schema(description = "Имя ребенка", example = "Лена")
    private String name;
    @Schema(description = "Пол ребенка", example = "Жен")
    private String gender;
    @Schema(description = "Путь к изображению", example = "http://res.cloudinary.com/hrfps8vte/image/upload/v1648720382/myimage.jpg")
    private String img_path;
    @JsonIgnore
    @Schema(description = "Путь к изображению", example = "http://res.cloudinary.com/hrfps8vte/image/upload/v1648720382/myimage.jpg")
    private String img;
    /** Добавить паттерн даты рождения. Format dd-MM-yyyy */
    @Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])[-,.](0?[1-9]|1[012])[-,.](19|20)?[0-9]{2}$")
    @Schema(description = "Дата рождения ребенка", example = "01.01.2015")
    private String dateOfBirth;
    /** Добавим позже паттерн для времени */
    @Schema(description = "Время рождения ребенка", example = "13:23:45")
    private String timeOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User parent;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "children_stage",
            joinColumns = @JoinColumn(name = "children_id"),
            inverseJoinColumns = @JoinColumn(name = "standart_stage_id"))
    private List<StandartStage> standartStages;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "children_user_stage",
            joinColumns = @JoinColumn(name = "children_id"),
            inverseJoinColumns = @JoinColumn(name = "user_stage_id"))
    private List<UserStage> userStages;

    //@OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<ChildrenWords> childrenWords;

    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ChildrenImg> childrenImg;

    public void addUserStage( UserStage userStage){
        this.userStages.add(userStage);
    }

    public void addChildrenImg(ChildrenImg addChildrenImg) {
        if(childrenImg == null){
            childrenImg = new ArrayList<>();
        }
        childrenImg.add(addChildrenImg);
    }

    /*public void addWords(ChildrenWords words){
        if(childrenWords == null){
            childrenWords = new ArrayList<>();
        }
        childrenWords.add(words);
    }*/
}
