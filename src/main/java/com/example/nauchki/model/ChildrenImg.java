package com.example.nauchki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Фото ребенка")
public class ChildrenImg {
    @Id
    @Schema(description = "ID записи", required = true, example = "1")
    private Long id;
    @Schema(description = "Путь к изображению", example = "http://res.cloudinary.com/hrfps8vte/image/upload/v1648720382/myimage.jpg")
    private String imgPath;
    @JsonIgnore
    @Schema(description = "Путь к изображению", example = "http://res.cloudinary.com/hrfps8vte/image/upload/v1648720382/myimage.jpg")
    private String img;
    @Column(length = 2048)
    @Schema(description = "Текст комментария", example = "Дочка играется")
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "children_id")
    private Children children;

    public ChildrenImg(String imgPath, String img, String comment) {
        this.imgPath = imgPath;
        this.img = img;
        this.comment = comment;
    }
}
