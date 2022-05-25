package com.example.nauchki.service;

import com.example.nauchki.exceptions.ResourceNotFoundException;
import com.example.nauchki.model.FileStorage;
import com.example.nauchki.repository.FileStorageRepository;
import com.example.nauchki.service.fileworker.UploadAndDeleteFileManager;
import com.example.nauchki.utils.FileContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class FileService {
    private final UploadAndDeleteFileManager fileManager;
    private final FileStorageRepository fileStorageRepository;

    public FileStorage initStorageFile(FileStorage storeFile, MultipartFile mpFile){
        storeFile.setName(mpFile.getOriginalFilename());
        storeFile.setType(mpFile.getContentType());
        storeFile.setSize(mpFile.getSize());
        String externalId = storeFile.getExternalId();
        if(externalId==null || externalId.isEmpty()) {
            storeFile.setExternalId(UUID.randomUUID().toString());
        }
        return storeFile;
    }

    public String changeFile(MultipartFile file, Long fileId) {
        String path="";
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found")
        );
        if(fileManager.deleteFile(fileStorage.getExternalId())){
            initStorageFile(fileStorage, file);
            path = fileManager.saveFile(file, fileStorage.getExternalId());
            fileStorage.setExternalPath(path);
            fileStorageRepository.save(fileStorage);
        }
        return path;
    }

    public void changeFileInfo(Long fileId, String tags, String description) {
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found")
        );
        fileStorage.setDescription(description);
        fileStorage.setTags(tags);
        fileStorageRepository.save(fileStorage);
    }

    public Long addComment(Long fileId, Long userId, String Comment){
        return 0L;
    }

    public void deleteComment(Long fileId, Long commentId){

    }
    
    public String saveAttachedFile(MultipartFile file, FileContainer entity) {
        FileStorage newFile = new FileStorage();
        newFile = initStorageFile(newFile, file);
        String path;
        path = fileManager.saveFile(file, newFile.getExternalId());
        if (!path.isEmpty()) {
            newFile.setOwnerType(entity.getEntityType());
            newFile.setOwnerId(entity.getEntityId());
            newFile.setExternalPath(path);
            entity.getFiles().add(newFile);
            fileStorageRepository.save(newFile);
        }
       return path;
    }

    public String saveAttachedFile(MultipartFile file, FileContainer entity, String tags, String description) {
        FileStorage newFile = new FileStorage();
        newFile = initStorageFile(newFile, file);
        String path;
        path = fileManager.saveFile(file, newFile.getExternalId());
        if (!path.isEmpty()) {
            newFile.setOwnerType(entity.getEntityType());
            newFile.setOwnerId(entity.getEntityId());
            newFile.setExternalPath(path);
            newFile.setTags(tags);
            newFile.setDescription(description);
            entity.getFiles().add(newFile);
            fileStorageRepository.save(newFile);
        }
        return path;
    }

    public String saveFile(MultipartFile file) {
        FileStorage newFile = new FileStorage();
        newFile = initStorageFile(newFile, file);
        String path;
        path = fileManager.saveFile(file, newFile.getExternalId());
        if (!path.isEmpty()) {
            fileStorageRepository.save(newFile);
        }
        return path;
    }

    public boolean deleteAttachedFile(Long fileId, FileContainer entity) {
        FileStorage atFile = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found"));
        boolean isOk = fileManager.deleteFile(atFile.getExternalId());
        if (isOk && entity != null) {
            List<FileStorage> fileStorage = entity.getFiles();
            fileStorage = fileStorage.stream()
                    .filter( v-> {
                                boolean isMatch= v.getId().equals(fileId);
                                if(isMatch){
                                    fileStorageRepository.delete(v);
                                }
                                return !isMatch;
                            }
                    )
                    .collect(Collectors.toList());
            entity.getFiles().clear();
            entity.getFiles().addAll(fileStorage);
        }
        return isOk;
    }

    public boolean deleteFile(Long fileId) {
        FileStorage atFile = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found"));
        boolean isOk = fileManager.deleteFile(atFile.getExternalId());
        if (isOk) {
            fileStorageRepository.delete(atFile);
        }
        return isOk;
    }

    public boolean deleteAllAttachedFiles(FileContainer entity) {
        List<FileStorage> fileStorage = entity.getFiles();
        fileStorage.stream()
                .forEach(v-> {
                            if(fileManager.deleteFile(v.getExternalId())){
                                fileStorageRepository.delete(v);
                            }
                        }
                );
        entity.getFiles().clear();
        return true;
    }

}
