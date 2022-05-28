package com.example.nauchki.model.dto;

import com.example.nauchki.model.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Статья")
public class PostDto {
    @Schema(description = "ID статьи", required = true, example = "1")
    private Integer id;
    @Schema(description = "Тэги для поиска", example = "питание")
    private String tag;
    @Schema(description = "Название статьи", example = "Чем кормить ребенка")
    private String title;
    @Schema(description = "Дополнение к названию статьи", example = "(описание продуктов для кормления ребенка)")
    private String subtitle;
    @Schema(description = "Текст статьи", example = "Ребенка надо кормить съедобными и питательными продуктами. ...")
    private String text;
    @Schema(description = "Путь к изображению", example = "http://res.cloudinary.com/hrfps8vte/image/upload/v1648720382/myimage.jpg")
    private String img;

    public PostDto(Integer id, String tag, String title, String subtitle, String text, String img) {
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.img = img;
    }

    public static PostDto valueOf(Post post){
        return new PostDto(
                post.getId(),
                post.getTag(),
                post.getTitle(),
                post.getSubtitle(),
                post.getText(),
                post.getImg_path()
        );
    }
}
