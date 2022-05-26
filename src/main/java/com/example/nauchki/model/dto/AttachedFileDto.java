package com.example.nauchki.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AttachedFileDto {

    private Long id;
    private String name;
    private String type;
    private String tags;
    private String description;
    private Long size;
    private String externalPath;

    private List<FileCommentDto> comments;

    private LocalDateTime createTime;

//    public static AttachedFileDto valueOf(FileStorage fileStorage) {
//        List<FileCommentDto> fileCommentDtos = fileStorage.getComments().stream().map(FileCommentDto::valueOf).collect(Collectors.toList());
//        return new AttachedFileDto(
//                fileStorage.getId(),
//                fileStorage.getName(),
//                fileStorage.getType(),
//                fileStorage.getTags(),
//                fileStorage.getDescription(),
//                fileStorage.getSize(),
//                fileStorage.getExternalPath(),
//                fileCommentDtos,
//                fileStorage.getCreateTime()
//                );
//    }
}
