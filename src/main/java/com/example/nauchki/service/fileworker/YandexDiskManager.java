package com.example.nauchki.service.fileworker;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
@Log4j2
public class YandexDiskManager implements UploadAndDeleteFileManager {

    private String token;
    private String cloudUrl;
    private HttpURLConnection connection;
    private String response;

    @Autowired
    public YandexDiskManager(@Value("${yandex.box.token}") String token,
                             @Value("${yandex.box.cloudUrl}") String cloudUrl) {
        this.token = token;
        this.cloudUrl = cloudUrl;
    }

    @Override
    public String saveFile(MultipartFile file, String ExternalId) {

        //    String fileName = file.getOriginalFilename();
        String uploadUrl = getUploadLink(ExternalId);

        File convFile = new File(ExternalId);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(convFile))
                .build();

        HttpPut request = new HttpPut(uploadUrl);
        request.setEntity(entity);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(request);

            log.info(
                    String.format("Response code when request save file:%s , message:%s, url:%s",
                            connection.getResponseCode(),
                            connection.getResponseMessage(), connection.getURL()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        publishFile(ExternalId);
        String path = getPublishLink(ExternalId);

        return path;
    }

    public String getUploadLink(String ExternalId) {

        JSONObject jsonObject;

        try {
            URL url = new URL(cloudUrl + "/resources/upload?path=/" + ExternalId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "OAuth " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            log.info(
                    String.format("Response code:%s , message:%s, metod:%s",
                            connection.getResponseCode(),
                            connection.getResponseMessage(), connection.getRequestMethod()));

            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                jsonObject = new JSONObject(sb.toString());
                return jsonObject.getString("href");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response;
    }

    public String publishFile(String fileName) {

        JSONObject jsonObject;

        try {
            URL url = new URL(cloudUrl + "/resources/publish?path=" + fileName);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "OAuth " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            log.info(
                    String.format("Response code when request publish file:%s , message:%s, metod:%s",
                            connection.getResponseCode(),
                            connection.getResponseMessage(), connection.getRequestMethod()));

            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                jsonObject = new JSONObject(sb.toString());
                return jsonObject.getString("href");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response;
    }

    public String getPublishLink(String ExternalId) {

        JSONObject jsonObject;

        try {
            URL url = new URL(cloudUrl + "/resources?path=" + ExternalId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "OAuth " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            log.info(
                    String.format("Response code when get publish Link:%s , message:%s, metod:%s",
                            connection.getResponseCode(),
                            connection.getResponseMessage(), connection.getRequestMethod()));

            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                jsonObject = new JSONObject(sb.toString());
                return jsonObject.getString("public_url");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response;
    }


    @Override
    public boolean deleteFile(String ExternalId) {
        try {
            URL url = new URL(cloudUrl + "/resources?path=" + ExternalId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "OAuth " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            log.info(
                    String.format("Response code when delete file:%s , message:%s",
                            connection.getResponseCode(),
                            connection.getResponseMessage()));

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

    //TODO при регистрации пользователя создавать его папку на Яндекс диске
    // для того чтобы сохранять файлы каждому пользователю в его папку
    // на Яндекс диске, в метод надо передавать email пользователя
    public void createDirectory(String email) {

        try {
            URL url = new URL(cloudUrl + "/resources?path=" + email);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "OAuth " + token);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            log.info(
                    String.format("Response code when create directory:%s , message:%s, metod:%s",
                            connection.getResponseCode(),
                            connection.getResponseMessage(), connection.getRequestMethod()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }
}
