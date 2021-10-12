package com.example.nauchki.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "standart_stage")
public class StandartStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer days;
    private Integer heightWHO;
    private Integer heightUSSR;
    private Integer weightWHO;
    private Integer weightUSSR;
    private String skills;
}
