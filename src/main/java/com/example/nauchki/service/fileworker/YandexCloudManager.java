package com.example.nauchki.service.fileworker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class YandexCloudManager implements UploadAndDeleteFileManager {

    private String token;
    private String cloudUrl;
    private HttpURLConnection connection;
    private String response;

    @Autowired
    public YandexCloudManager(@Value("${yandex.box.token}")String token,
                              @Value("${yandex.box.cloudUrl}")String cloudUrl) {
        this.token = token;
        this.cloudUrl = cloudUrl;
    }


    @Override
    public String saveFile(MultipartFile file) {
        try {
            URL url = new URL(cloudUrl + "/" + file.getOriginalFilename());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            connection.setRequestProperty("Content-Length", Integer.toString(file.getBytes().length));
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            if (!file.isEmpty()) {
                byte[] postDataBytes = file.getBytes();
                try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                    writer.write(postDataBytes);
                    writer.flush();
                } catch (Exception e) {
                    return e.getMessage(); //
                }
            }
            System.out.println(
                    String.format("Response code:%s , message:%s",
                            connection.getResponseCode(),
                            connection.getResponseMessage()));
            if (connection.getResponseCode() != 200) {

                return connection.getErrorStream().toString();
            }
            System.out.println(response = connection.getURL().toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response;
    }


    @Override
    public boolean deleteFile(String filename) {

        try {
            URL url = new URL(cloudUrl +"/" + filename);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(false);

            if (connection.getResponseCode() == 204) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return false;
    }

}
