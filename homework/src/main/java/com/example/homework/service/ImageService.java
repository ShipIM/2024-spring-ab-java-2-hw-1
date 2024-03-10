package com.example.homework.service;

import com.example.homework.model.entity.jpa.Image;

import java.util.List;

public interface ImageService {

    Image saveMeta(Image image);

    List<Image> getAllMeta();

    Image getMeta(String reference);

}
