package com.example.nauchki.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Путь к файлу")
public class FilePathDto {
    String externalPath;
}
