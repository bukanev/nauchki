package com.example.nauchki.utils;

import com.example.nauchki.model.FileStorage;

import java.util.List;

/**
 * Entities that contain files should implement this interface
 * It must also contain a field for attached files:
 * EXAMPLE:
 *     @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 *      @JoinTable(name = "attached_files_user_images",
 *          joinColumns = @JoinColumn(name = "id"),
 *          inverseJoinColumns = @JoinColumn(name="file_id", referencedColumnName = "id"))
 *     private List<FileStorage> images;
 *
 *  and must implements the methods:
 *          getFiles() - getter for field 'files'
 *          setFiles() - setter for field 'files'
 *          getEntityId() - must return id of entity
 *          getEntityType() - must return entity type name (example: return "user_images";)
 */
public interface FileContainer {

    List<FileStorage> getFiles();
    void setFiles(List<FileStorage> images);
    Long getEntityId();
    String getEntityType();
}
