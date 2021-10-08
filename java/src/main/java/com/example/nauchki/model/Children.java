package com.example.nauchki.model;

import com.example.nauchki.model.dto.ChildrenDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "children")
public class Children {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;

    /** Добавить паттерн даты рождения. Format dd-MM-yyyy */
    @Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])[-,.](0?[1-9]|1[012])[-,.](19|20)?[0-9]{2}$")
    private String dateOfBirth;
    /** Добавим позже паттерн для времени */
    private String timeOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User parent;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "standart_stage",
            joinColumns = @JoinColumn(name = "children_id"),
            inverseJoinColumns = @JoinColumn(name = "standart_stage_id"))
    private List<StandartStage> standartStages;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_stage",
            joinColumns = @JoinColumn(name = "children_id"),
            inverseJoinColumns = @JoinColumn(name = "user_stage_id"))
    private List<UserStage> userStages;

    public ChildrenDto mapToChildrenDto(){
        ChildrenDto children = new ChildrenDto();
        children.setId(id);
        children.setName(name);
        children.setGender(gender);
        children.setDateOfBirth(dateOfBirth);
        children.setTimeOfBirth(timeOfBirth);
        children.setStandartStages(standartStages);
        children.setUserStages(userStages);
        return children;
    }
}
