package com.example.homework.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    void upload(MultipartFile file, String reference);

    byte[] download(String reference);

}
