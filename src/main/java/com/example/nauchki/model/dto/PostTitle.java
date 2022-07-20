package com.example.nauchki.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Статья кратко")
public class PostTitle {
    Long id;
    String Title;
    String tag;
    UserNameDto author;
    List<FilePathDto> images;
}
