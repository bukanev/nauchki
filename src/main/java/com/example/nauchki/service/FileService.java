package com.example.nauchki.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.nauchki.utils.FileContainer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class FileService {
    private final Cloudinary cloudinary;

    @Value("${upload.path}")
    private String uploadPath;
    private String localDir = System.getProperty("user.dir");

    public String saveFile(MultipartFile file, FileContainer entity) {
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
                    ObjectUtils.asMap("public_id", resultFilename)));
            path = String.valueOf(object.get("url"));
            if(entity!=null){
                entity.setImg(resultFilename);
                entity.setImg_path(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    public boolean deleteFile(String filename, FileContainer entity){
        JSONObject object = new JSONObject();
        try {
            object = new JSONObject(cloudinary.uploader().destroy(filename,null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Boolean isOk = String.valueOf(object.get("result")).equals("ok");
        if(isOk && entity!=null){
            entity.setImg("");
            entity.setImg_path("");
        }
        return isOk;
    }
}
