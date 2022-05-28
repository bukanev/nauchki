package com.example.nauchki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Schema(description = "Статья")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "ID статьи", required = true, example = "1")
    private Integer id;
    @Schema(description = "Тэги для поиска", example = "питание")
    private String tag;
    @Schema(description = "Название статьи", example = "Чем кормить ребенка")
    private String title;
    @Schema(description = "Дополнение к названию статьи", example = "(описание продуктов для кормления ребенка)")
    private String subtitle;
    @Column(length = 5000)
    @Schema(description = "Текст статьи", example = "Ребенка надо кормить съедобными и питательными продуктами. ...")
    private String text;
    @Schema(description = "Путь к изображению", example = "http://res.cloudinary.com/hrfps8vte/image/upload/v1648720382/myimage.jpg")
    private String img_path;
    @JsonIgnore
    @Schema(description = "Путь к изображению", example = "http://res.cloudinary.com/hrfps8vte/image/upload/v1648720382/myimage.jpg")
    private String img;

    public Post() {
    }

    public Post(String tag, String title, String subtitle, String text) {
        this.tag = tag;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTag() {
        return tag;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String filename) {
        this.img_path = filename;
    }
}
