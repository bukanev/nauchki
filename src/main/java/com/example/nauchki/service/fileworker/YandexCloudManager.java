package com.example.nauchki.service.fileworker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@RequiredArgsConstructor
@Component
public class YandexCloudManager implements UploadAndDeleteFileManager {

    private String token = ""; // TO DO insert token
    private String cloudUrl = ""; // TO DO insert baken URL
    private HttpURLConnection connection = null;
    private String response = "";

    @Override
    public String saveFile(MultipartFile file) {

        try {
            //Create connection
            URL url = new URL(cloudUrl + file.getOriginalFilename());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data");
            connection.setRequestProperty("Content-Length",
                    Integer.toString(file.getBytes().length));
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            if (!file.isEmpty()) {
                byte[] postDataBytes = file.getBytes();
                try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                    writer.write(postDataBytes);
                    writer.flush();

                } catch (Exception e) {
                    return e.getMessage(); //
                }
            }

            //Send response
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
            //Create connection
            URL url = new URL(cloudUrl + filename);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(false);

            //Send response
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
