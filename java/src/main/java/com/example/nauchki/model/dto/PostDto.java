package com.example.nauchki.model.dto;

import com.example.nauchki.model.Post;
import lombok.Data;

@Data
public class PostDto {
    private Integer id;
    private String tag;
    private String text;
    private String author;
    private String img;

    public PostDto(Integer id, String tag, String text, String author, String img) {
        this.id = id;
        this.tag = tag;
        this.text = text;
        this.author = author;
        this.img = img;
    }

    public static PostDto valueOf(Post post){
        return new PostDto(
                post.getId(),
                post.getTag(),
                post.getText(),
                post.getAuthor().getUsername(),
                post.getImg_path()
        );
    }
}
