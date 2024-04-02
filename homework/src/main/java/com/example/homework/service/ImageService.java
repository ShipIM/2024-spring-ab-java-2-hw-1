package com.example.homework.service;

import com.example.homework.dto.image.ResponseImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ResponseImage> getAllMeta();

    ResponseImage getMeta(String reference);

    ResponseImage saveImage(MultipartFile file);

    byte[] download(String reference);

}
