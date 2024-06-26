package com.example.homework.service.impl;

import com.example.homework.annotation.Audit;
import com.example.homework.config.minio.MinioProperties;
import com.example.homework.exception.UncheckedMinioException;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.service.MinioService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient client;
    private final MinioProperties properties;

    @Audit(message = "image saved", type = OperationType.WRITE)
    public void upload(MultipartFile file, String reference) {
        try {
            InputStream stream = new ByteArrayInputStream(file.getBytes());
            client.putObject(
                    PutObjectArgs.builder()
                            .bucket(properties.getBucket())
                            .object(reference)
                            .stream(stream, file.getSize(), properties.getImageSize())
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new UncheckedMinioException(e.getMessage());
        }
    }

    @Cacheable(value = "MinioService::download", key = "#reference")
    @Audit(message = "image downloaded", type = OperationType.READ)
    public byte[] download(String reference) {
        try {
            return IOUtils.toByteArray(
                    client.getObject(
                            GetObjectArgs.builder()
                                    .bucket(properties.getBucket())
                                    .object(reference)
                                    .build()
                    )
            );
        } catch (Exception e) {
            throw new UncheckedMinioException(e.getMessage());
        }
    }

}
