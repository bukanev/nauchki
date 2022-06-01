package com.example.nauchki.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment")
@Schema(description = "Комментарии к описаниям этапов жизни ребенка")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID записи", required = true, example = "1")
    private Long id;
    @Column(length = 2048)
    @Schema(description = "Текст комментария", example = "Дочка иногда становиться настороженней")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_stage_id")
    private UserStage userstage;
}
