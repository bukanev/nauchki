package com.example.nauchki.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Пока не актуально.
 */
@Service
public class SmsService {

    /*public boolean sendSmS(String toNumber, String fromNumber, String text){
        try {

            // Данные для отправки SMS

            // Логин и пароль
            String accountSid = "AC641dcfe991a9088f5761b86e55fb5bbf";
            String authToken = "121403de297ed2c18cc39436a26217b9";
            String nameAndPassword = accountSid + ":" + authToken;
            // кодируем логин и пароль
            String encoding = Base64.encodeBase64String((nameAndPassword
                    .getBytes(StandardCharsets.UTF_8)));

            String charset = "UTF-8";
            // кодируем значения параметров, которые будем передовать
            String toEncode = URLEncoder.encode(toNumber, charset);
            String fromEncode = URLEncoder.encode(fromNumber, charset);
            String bodyEncode = URLEncoder.encode(text, charset);

            // формируем строку с параметрами
            String query = "To=" + toEncode + "&From=" + fromEncode + "&Body="
                    + bodyEncode;

            // создаем адрес по котору будем обращаться
            String adddr = "https://api.twilio.com/2010-04-01/Accounts/"
                    + accountSid + "/SMS/Messages";

            URL url = new URL(adddr);

            // открываем подключение - кодированное https
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Authorization", "Basic " + encoding);
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=" + "utf-8");

            // получаем выходной поток
            OutputStream out = connection.getOutputStream();
            // пишем в выходной поток
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.append(query);
            writer.flush();
            writer.close();
            out.close();

            // получаем код ответа от сервера
            System.out.println(connection.getResponseCode());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    public boolean sendSms(String phone, String text, String sender){
        try {
            String name = "Rail437";
            String password = "Qq112233";

            String authString = name + ":" + password;
            String authStringEnc = Base64.encodeBase64String(authString.getBytes());

            URL url = new URL("http","api.smsfeedback.ru",80,"/messages/v2/send/?phone=%2B"+phone+"&text="+ URLEncoder.encode(text, "UTF-8")+"&sender="+sender);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", authStringEnc);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();

            System.out.println("*** BEGIN ***");
            System.out.println(result);
            System.out.println("*** END ***");

            return true;
        } catch (MalformedURLException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return false;
    }
}
