package com.example.homework.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    String upload(MultipartFile file) throws Exception;

    byte[] download(String reference) throws Exception;

}
