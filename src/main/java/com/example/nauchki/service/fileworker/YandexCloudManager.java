package com.example.nauchki.service.fileworker;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Primary
@Log4j2
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
    public String saveFile(MultipartFile file, String externalId) {
        try {
            URL url = new URL(cloudUrl + externalId);
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

            if (connection.getResponseCode() != 200) {
                log.info(
                        String.format("Response:%s , message:%s",
                                connection.getResponseCode(), connection.getResponseMessage()));
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
    public boolean deleteFile(String externalId) {

        try {
            URL url = new URL(cloudUrl + externalId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(false);

            if (connection.getResponseCode() != 204) {
                log.info(
                        String.format("Response:%s , message:%s",
                                connection.getResponseCode(), connection.getResponseMessage()));
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return true;
    }
}
