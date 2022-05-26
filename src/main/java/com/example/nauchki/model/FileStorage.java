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
@Table(name = "files_torage")
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ownerId")
    private Long ownerId; // идентификатор владельца файла, можно использовать для определения владельца файла
    @Column(name = "ownerType")
    private String ownerType; //тип владельца файла, можно использовать для определения владельца файла
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
    private String externalId; //идентификатор файла ф облачном хранилище
    @Column(name = "externalPath")
    private String externalPath; //путь к файлу в облачном хранилище

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
