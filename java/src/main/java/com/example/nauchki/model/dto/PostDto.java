package com.example.nauchki.model.dto;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import lombok.Data;

import java.io.File;

@Data
public class PostDto {
    private Integer id;
    private String tag;
    private String text;
    private User author;
    private String img;

    public PostDto(Integer id, String tag, String text, User author, String img) {
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
                post.getAuthor(),
                post.getImg_path()
        );
    }
}
