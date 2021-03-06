package com.example.nauchki.service.fileworker;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CloudinaryManager implements UploadAndDeleteFileManager {
    private final Cloudinary cloudinary;
    @Value("${upload.path}")
    private String uploadPath;
    private String localDir = System.getProperty("user.dir");

    @Override
    public String saveFile(MultipartFile file, String ExternalId) {
        String path;
        try {

            File uploadDir = new File(localDir + uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String resultFilename = file.getOriginalFilename();
            File img = new File(localDir + uploadPath + "/", resultFilename);
            file.transferTo(img);
            JSONObject object = new JSONObject(cloudinary.uploader().upload(img,
                    ObjectUtils.asMap("public_id", ExternalId)));
            path = String.valueOf(object.get("url"));
        } catch (
                IOException e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    @Override
    public boolean deleteFile(String ExternalId) {
        JSONObject object = new JSONObject();
        try {
            object = new JSONObject(cloudinary.uploader().destroy(ExternalId,null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(object.get("result")).equals("ok");
    }
}
