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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class FileService {
    private final UploadAndDeleteFileManager fileManager;
    private final FileStorageRepository fileStorageRepository;

    public FileStorage fillStorageFile(FileStorage storeFile, MultipartFile mpFile){
        storeFile.setName(mpFile.getOriginalFilename());
        storeFile.setType(mpFile.getContentType());
        storeFile.setSize(mpFile.getSize());
        storeFile.setExternalId(mpFile.getOriginalFilename());
        return storeFile;
    }

    public String changeFile(MultipartFile file, Long fileId) {
        String path="";
        FileStorage fileStorage = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found")
        );
        if(fileManager.deleteFile(fileStorage.getExternalId())){
            path = fileManager.saveFile(file);
            fillStorageFile(fileStorage, file);
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
        String path;
        String resultFilename = file.getOriginalFilename();
        path = fileManager.saveFile(file);
        if (entity != null & !path.isEmpty()) {
            List<FileStorage> fileStorage = entity.getImages();
            FileStorage sameFile = fileStorage.stream()
                    .filter((v)->v.getName().equals(resultFilename))
                    .findFirst().orElseGet(()->{
                        fileStorage.add(new FileStorage());
                        return fileStorage.get(fileStorage.size()-1);
                    });
            sameFile = fillStorageFile(sameFile, file);
            sameFile.setOwnerType(entity.getEntityType());
            sameFile.setOwnerId(entity.getEntityId());
            fileStorageRepository.save(sameFile);
        }
        return path;
    }

    public String saveFile(MultipartFile file) {
        String path;
        path = fileManager.saveFile(file);
        if (!path.isEmpty()) {
            FileStorage sameFile = new FileStorage();
            sameFile = fillStorageFile(sameFile, file);
            fileStorageRepository.save(sameFile);
        }
        return path;
    }

    public String saveAttachedFile(MultipartFile file, FileContainer entity, String tags, String description) {
        String path;
        String resultFilename = file.getOriginalFilename();
        path = fileManager.saveFile(file);
        if (entity != null & !path.isEmpty()) {
            List<FileStorage> fileStorage = entity.getImages();
            FileStorage sameFile = fileStorage.stream()
                    .filter((v)->v.getName().equals(resultFilename))
                    .findFirst().orElseGet(()->{
                        fileStorage.add(new FileStorage());
                        return fileStorage.get(fileStorage.size()-1);
                    });
            sameFile = fillStorageFile(sameFile, file);
            sameFile.setTags(tags);
            sameFile.setDescription(description);
            fileStorageRepository.save(sameFile);
        }
        return path;
    }

    public boolean deleteAttachedFile(Long fileId, FileContainer entity) {
        FileStorage atFile = fileStorageRepository.findById(fileId).orElseThrow(
                ()->new ResourceNotFoundException("File with id '" + fileId + "' not found"));
        boolean isOk = fileManager.deleteFile(atFile.getExternalId());
        if (isOk && entity != null) {
            List<FileStorage> fileStorage = entity.getImages();
            fileStorage = fileStorage.stream()
                    .filter((v)-> {
                                boolean isMatch= v.getId().equals(fileId);
                                if(isMatch){
                                    fileStorageRepository.delete(v);
                                }
                                return !isMatch;
                            }
                    )
                    .collect(Collectors.toList());
            entity.getImages().clear();
            entity.getImages().addAll(fileStorage);
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
        List<FileStorage> fileStorage = entity.getImages();
        fileStorage.stream()
                .forEach((v)-> {
                            if(fileManager.deleteFile(v.getExternalId())){
                                fileStorageRepository.delete(v);
                            }
                        }
                );
        entity.getImages().clear();
        return true;
    }

}
