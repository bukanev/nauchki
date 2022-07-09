package com.example.nauchki.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Статья")
@AllArgsConstructor
@Builder
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
    @Schema(description = "Автор статьи")
    private UserNameDto author;
    private List<AttachedFileDto> images;
}
