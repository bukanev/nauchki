package com.example.nauchki.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "filestorage")
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ownerId")
    private Long ownerId;
    @Column(name = "ownerType")
    private String ownerType;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "tags")
    private String tags;
    @Column(name = "description")
    private String description;
    @Column(name = "size")
    private Long size;
    @Column(name = "externalId")
    private String externalId;
    @Column(name = "externalPath")
    private String externalPath;

    @OneToMany
    @JoinColumn(name="fileId")
    private List<FileComment> comments;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createTime;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updateTime;


}
