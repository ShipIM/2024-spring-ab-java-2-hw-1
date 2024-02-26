package com.example.homework.config;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Bean
    public MinioClient minioClient(MinioProperties properties) throws Exception {
        MinioClient client = MinioClient.builder()
                .credentials(
                        properties.getAccessKey(),
                        properties.getSecretKey())
                .endpoint(
                        properties.getUrl(),
                        properties.getPort(),
                        properties.isSecure())
                .build();

        client.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(properties.getBucket())
                        .build()
        );

        return client;
    }

}
