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
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Primary
@Component
@Log4j2
public class YandexDiskManager implements UploadAndDeleteFileManager {

    private String token;
    private String cloudUrl;
    private HttpURLConnection connection;

    @Autowired
    public YandexDiskManager(@Value("${yandex.box.token}") String token,
                             @Value("${yandex.box.cloudUrl}") String cloudUrl) {
        this.token = token;
        this.cloudUrl = cloudUrl;
    }

    @Override
    public String saveFile(MultipartFile file, String ExternalId) {
        String path = "";

        try {
            String uploadUrl = getUploadLink(ExternalId);

            File convFile = new File(ExternalId);
            FileOutputStream fos = null;

            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();


            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("file", new FileBody(convFile))
                    .build();

            HttpPut request = new HttpPut(uploadUrl);
            request.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = null;

            response = client.execute(request);

            if (connection.getResponseCode() != 200) {
                log.info(String.format("Response code:%s , message:%s",
                        connection.getResponseCode(), connection.getResponseMessage()));
            }

            publishFile(ExternalId);
            path = getPublishLink(ExternalId);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return path;
    }

    public String getUploadLink(String ExternalId) throws IOException {

        String requestUrl = "/resources/upload?path=/" + ExternalId;
        String requestMethod = "GET";

        getConnection(requestMethod, requestUrl);

        if (connection.getResponseCode() == 200) {
            JSONObject jsonObject = getJsonFromResponse();
            return jsonObject.getString("href");
        }
        log.info(
                String.format("Response code:%s , message:%s",
                        connection.getResponseCode(), connection.getResponseMessage()));
        return null;
    }


    public void publishFile(String fileName) throws IOException {

        String requestUrl = "/resources/publish?path=" + fileName;
        String requestMethod = "PUT";

        getConnection(requestMethod, requestUrl);

        if (connection.getResponseCode() != 200) {
            log.info(String.format("Response code:%s , message:%s",
                    connection.getResponseCode(), connection.getResponseMessage()));
        }
    }

    public String getPublishLink(String ExternalId) throws IOException {

        String requestUrl = "/resources?path=" + ExternalId;
        String requestMethod = "GET";

        getConnection(requestMethod, requestUrl);

        if (connection.getResponseCode() == 200) {
            JSONObject jsonObject = getJsonFromResponse();
            return jsonObject.getString("public_url");
        }
        log.info(
                String.format("Response code when get publish Link:%s , message:%s",
                        connection.getResponseCode(), connection.getResponseMessage()));
        return connection.getResponseMessage();
    }

    @Override
    public boolean deleteFile(String ExternalId) {

        String requestUrl = "/resources?path=" + ExternalId;
        String requestMethod = "DELETE";

        try {
            getConnection(requestMethod, requestUrl);

            if (connection.getResponseCode() == 204) {
                return true;
            }
            log.info(
                    String.format("Response code when delete file:%s , message:%s",
                            connection.getResponseCode(), connection.getResponseMessage()));

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

        String requestUrl = "/resources?path=" + email;
        String requestMethod = "PUT";

        try {
            getConnection(requestMethod, requestUrl);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    public void getConnection(String requestMethod, String requestUrl) throws IOException {

        URL url = new URL(cloudUrl + requestUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Authorization", "OAuth " + token);
        connection.setUseCaches(false);
        connection.setDoOutput(true);
    }

    public JSONObject getJsonFromResponse() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        JSONObject jsonObject = new JSONObject(sb.toString());
        return jsonObject;
    }
}
