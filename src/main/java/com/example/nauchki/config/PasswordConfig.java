package com.example.nauchki.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Файл с данными подключения и получения доступа к Cloudinary
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "hrfps8vte",
            "api_key", "461477523392492",
            "api_secret", "25Cejjt6x-nQrchQz-CwFClF9g8"));

    @Bean
    public Cloudinary getCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hrfps8vte",
                "api_key", "461477523392492",
                "api_secret", "25Cejjt6x-nQrchQz-CwFClF9g8"));
    }
}
