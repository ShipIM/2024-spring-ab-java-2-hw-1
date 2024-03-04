package com.example.homework.service;

import com.example.homework.config.minio.MinioProperties;
import com.example.homework.model.entity.mongo.Operation;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
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
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final OperationService operationService;
    private final MinioClient client;
    private final MinioProperties properties;

    public String upload(MultipartFile file) throws Exception {
        String reference = UUID.randomUUID().toString();

        InputStream stream = new ByteArrayInputStream(file.getBytes());
        client.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(reference)
                        .stream(stream, file.getSize(), properties.getImageSize())
                        .contentType(file.getContentType())
                        .build()
        );

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Image saved to database: %s", reference))
                        .time(LocalDateTime.now())
                        .type(OperationType.WRITE)
                        .build()
        );

        return reference;
    }

    @Cacheable(value = "MinioService::download", key = "#reference")
    public byte[] download(String reference) throws Exception {
        byte[] contents = IOUtils.toByteArray(
                client.getObject(
                        GetObjectArgs.builder()
                                .bucket(properties.getBucket())
                                .object(reference)
                                .build()
                )
        );

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Image downloaded from database: %s", reference))
                        .time(LocalDateTime.now())
                        .type(OperationType.READ).build()
        );

        return contents;
    }

}
