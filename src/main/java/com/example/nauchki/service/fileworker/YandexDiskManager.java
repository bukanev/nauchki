package com.example.nauchki.service.fileworker;

import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
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
    private final String requestUrlPublish = "/resources/publish?path=";
    private final String requestUrlGetMetaInfo = "/resources?path=";
    private final String requestUrlGetUpload = "/resources/upload?path=/";

    @Autowired
    public YandexDiskManager(@Value("${yandex.token}") String token,
                             @Value("${yandex.path}") String cloudUrl) {
        this.token = token;
        this.cloudUrl = cloudUrl;
    }

    @Override
    public String saveFile(MultipartFile file, String externalId) {

        String path = "";

        try {
            String uploadUrl = requestHTTP(externalId, requestUrlGetUpload, "GET", 200);

            File convFile = new File(externalId);
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("file", new FileBody(convFile))
                    .build();

            HttpPut request = new HttpPut(uploadUrl);
            request.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            client.execute(request);

            requestHTTP(externalId, requestUrlPublish, "PUT", 200);
            path = requestHTTP(externalId, requestUrlGetMetaInfo, "GET", 200);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    public boolean deleteFile(String ExternalId) {

        try {
            HttpURLConnection connection = getConnection("DELETE", requestUrlGetMetaInfo + ExternalId, 204);
            if (connection.getResponseCode() == 204) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public HttpURLConnection getConnection(String requestMethod, String requestUrl, Integer expectedResponseCode) throws IOException {

        HttpURLConnection connection;
        URL url = new URL(cloudUrl + requestUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Authorization", "OAuth " + token);
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        if (connection.getResponseCode() != expectedResponseCode) {
            log.info(
                    String.format("Response:%s , message:%s",
                            connection.getResponseCode(), connection.getResponseMessage()));
            return null;
        }
        return connection;
    }

    public String requestHTTP(String externalId, String requestUrl, String requestMethod, Integer expectedResponseCode) throws IOException {

        HttpURLConnection connection = getConnection(requestMethod, requestUrl + externalId, expectedResponseCode);
        JSONObject jsonObject = getJsonFromResponse(connection);

        switch (requestUrl) {
            case requestUrlGetMetaInfo:
                return jsonObject.getString("public_url");
            case requestUrlGetUpload:
                return jsonObject.getString("href");
            default:
                return null;
        }
    }

    public JSONObject getJsonFromResponse(HttpURLConnection connection) {
        JSONObject jsonObject = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            jsonObject = new JSONObject(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

