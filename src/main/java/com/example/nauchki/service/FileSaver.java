package com.example.nauchki.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class FileSaver {
    private final Cloudinary cloudinary;

    @Value("${upload.path}")
    private String uploadPath;
    private String localDir = System.getProperty("user.dir");

    public String saveFile(MultipartFile file) {
        String path = new String();
        try {
            File uploadDir = new File(localDir + uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String resultFilename = file.getOriginalFilename();
            File img = new File(localDir + uploadPath + "/" , resultFilename);
            file.transferTo(img);
            JSONObject object = new JSONObject(cloudinary.uploader().upload(img,
                    ObjectUtils.asMap("public_id", file.getOriginalFilename())));
            path = String.valueOf(object.get("url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
