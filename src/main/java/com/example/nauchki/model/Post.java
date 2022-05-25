package com.example.nauchki.model;

import com.example.nauchki.utils.FileContainer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Post implements FileContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    private String title;
    private String subtitle;
    @Column(length = 5000)
    private String text;
//    private String img_path;
//    @JsonIgnore
//    private String img;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "attached_files_post_images",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name="file_id", referencedColumnName = "id"))
    private List<FileStorage> images;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTag() {
        return tag;
    }

//    public String getImg_path() {
//        return img_path;
//    }
//
//    public void setImg_path(String filename) {
//        this.img_path = filename;
//    }

    @Override
    public String getEntityType() {
        return "post_images";
    }

    @Override
    public Long getEntityId() {
        return this.id;
    }


}
