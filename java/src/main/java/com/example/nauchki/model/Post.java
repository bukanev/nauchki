package com.example.nauchki.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String tag;
    private String title;
    private String subtitle;
    private String text;
    private String img_path;

    public Post() {
    }

    public Post(String tag, String title, String subtitle, String text) {
        this.tag = tag;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTag() {
        return tag;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String filename) {
        this.img_path = filename;
    }
}
