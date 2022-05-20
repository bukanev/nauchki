package com.example.nauchki.service;

import com.example.nauchki.service.fileworker.UploadAndDeleteFileManager;
import com.example.nauchki.utils.FileContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class FileService {
    private final UploadAndDeleteFileManager fileManager;

    public String saveFile(MultipartFile file, FileContainer entity) {
        String path;
        String resultFilename = file.getOriginalFilename();
        path = fileManager.saveFile(file);
        if (entity != null & !path.isEmpty()) {
            entity.setImg(resultFilename);
            entity.setImg_path(path);
        }
        return path;
    }

    public boolean deleteFile(String filename, FileContainer entity) {
        Boolean isOk = fileManager.deleteFile(filename);
        if (isOk && entity != null) {
            entity.setImg("");
            entity.setImg_path("");
        }
        return isOk;
    }
}
