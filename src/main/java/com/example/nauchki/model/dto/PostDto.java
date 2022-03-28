package com.example.nauchki.model.dto;

import com.example.nauchki.model.Post;
import lombok.Data;

@Data
public class PostDto {
    private Integer id;
    private String tag;
    private String title;
    private String subtitle;
    private String text;
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
