package com.example.nauchki.service.fileworker;


import org.springframework.web.multipart.MultipartFile;

public interface UploadAndDeleteFileManager {
    String saveFile(MultipartFile file, String ExternalId);
    boolean deleteFile(String ExternalId);
}