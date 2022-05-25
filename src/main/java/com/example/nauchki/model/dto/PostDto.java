package com.example.nauchki.model.dto;

import com.example.nauchki.model.Post;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDto {
    private Long id;
    private String tag;
    private String title;
    private String subtitle;
    private String text;
    private List<AttachedFileDto> images;

    public PostDto(Long id, String tag, String title, String subtitle, String text, List<AttachedFileDto> images) {
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.images = images;
    }

    public static PostDto valueOf(Post post){
        List<AttachedFileDto> filesDtos = post.getFiles().stream().map(AttachedFileDto::valueOf).collect(Collectors.toList());
        return new PostDto(
                post.getId(),
                post.getTag(),
                post.getTitle(),
                post.getSubtitle(),
                post.getText(),
                filesDtos
        );
    }
}
