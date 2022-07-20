package com.example.nauchki.model;

import com.example.nauchki.utils.FileContainer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Schema(description = "Статья")
@AllArgsConstructor
@Builder
public class Post implements FileContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID статьи", required = true, example = "1")
    private Long id;
    @Schema(description = "Тэги для поиска", example = "питание")
    private String tag;
    @Schema(description = "Название статьи", example = "Чем кормить ребенка")
    private String title;
    @Schema(description = "Дополнение к названию статьи", example = "(описание продуктов для кормления ребенка)")
    private String subtitle;
    @Column(length = 5000)
    @Schema(description = "Текст статьи", example = "Ребенка надо кормить съедобными и питательными продуктами. ...")
    private String text;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "attached_files_post_images",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name="file_id", referencedColumnName = "id"))
    private List<FileStorage> images;

    @ManyToOne
    private User author;

    public Post() {
        this.images = new ArrayList<>();
    }

    public Post(String tag, String title, String subtitle, String text) {
        this.tag = tag;
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.images = new ArrayList<>();
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

    @Override
    public String getEntityType() {
        return "post_images";
    }

    @Override
    public Long getEntityId() {
        return this.id;
    }

    @Override
    public List<FileStorage> getFiles() {
        if(this.images==null){
            this.images = new ArrayList<>();
        }
        return this.images;
    }

    @Override
    public void setFiles(List<FileStorage> images) {
        this.images = images;
    }

}
