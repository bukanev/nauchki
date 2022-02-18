package com.example.nauchki.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2048)
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_stage_id")
    private UserStage userstage;
}
