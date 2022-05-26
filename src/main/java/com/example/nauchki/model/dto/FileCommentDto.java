package com.example.nauchki.model.dto;

import com.example.nauchki.model.FileComment;
import com.example.nauchki.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileCommentDto {

    private Long id;
    @NotEmpty
    private String comment;
    @NotEmpty
    private Long userId;
    private String userName;
    private LocalDateTime createTime;

}
