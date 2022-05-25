package com.example.nauchki.utils;

import com.example.nauchki.model.FileStorage;

import java.util.List;

/**
 * Entities that contain files should implement this interface
 * It must also contain a field:
 *      @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 *      @JoinTable(name = "attached_files_user",
 *          joinColumns = @JoinColumn(name = "id"),
 *          inverseJoinColumns = @JoinColumn(name="file_id", referencedColumnName = "id"))
 *      private List<AttachedFile> files;
 *  and must implements the methods:
 *          getFiles() - getter for field 'files'
 *          setFiles() - setter for field 'files'
 *          getEntityId() - must return id of entity
 *          getEntityType() - must return entity type name
 */
public interface FileContainer {

    List<FileStorage> getImages();
    void setImages(List<FileStorage> images);
    Long getEntityId();
    String getEntityType();
}
