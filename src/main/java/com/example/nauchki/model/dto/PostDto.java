package com.example.nauchki.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

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
    @Schema(description = "ID автора статьи", example = "1")
    private Long authorId;
    @Schema(description = "Имя автора статьи", example = "Иванов Иван Иваныч")
    private String authorName;
    private List<AttachedFileDto> images;
//    public PostDto(Long id, String tag, String title, String subtitle, String text, List<AttachedFileDto> images) {
//        this.id = id;
//        this.tag = tag;
//        this.title = title;
//        this.subtitle = subtitle;
//        this.text = text;
//        this.images = images;
//    }
//
//    public static PostDto valueOf(Post post){
//        //List<AttachedFileDto> filesDtos = post.getFiles().stream().map(AttachedFileDto::valueOf).collect(Collectors.toList());
//        return new PostDto(
//                post.getId(),
//                post.getTag(),
//                post.getTitle(),
//                post.getSubtitle(),
//                post.getText(),
//                new ArrayList<>()
//        );
//    }

}
