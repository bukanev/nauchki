package com.example.nauchki.utils;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface FileContainer {

    String getImg();
    String getImg_path();
    void setImg_path(String img_path);
    void setImg(String img);

}
