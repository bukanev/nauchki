package com.example.nauchki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChildrenImg {
    @Id
    private Long id;
    private String imgPath;
    @JsonIgnore
    private String img;
    @Column(length = 2048)
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "children_id")
    private Children children;

    public ChildrenImg(String imgPath, String img, String comment) {
        this.imgPath = imgPath;
        this.img = img;
        this.comment = comment;
    }
}
