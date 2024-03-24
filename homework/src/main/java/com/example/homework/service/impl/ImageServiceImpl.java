package com.example.homework.service.impl;

import com.example.homework.annotation.Audit;
import com.example.homework.dto.image.ResponseImage;
import com.example.homework.dto.mapper.ImageMapper;
import com.example.homework.model.entity.jpa.Image;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.ImageRepository;
import com.example.homework.service.ImageService;
import com.example.homework.service.MinioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final MinioService minioService;
    private final ImageMapper imageMapper;

    @Transactional
    @CacheEvict(value = "ImageService::getAllMeta", allEntries = true)
    @Audit(message = "meta saved", type = OperationType.WRITE)
    public ResponseImage saveImage(MultipartFile multipartFile) throws Exception {
        String reference = UUID.randomUUID().toString();

        Image image = imageMapper.toImage(multipartFile, reference);
        image = imageRepository.save(image);

        minioService.upload(multipartFile, reference);

        return imageMapper.toResponse(image);
    }

    @Cacheable(value = "ImageService::getAllMeta")
    @Audit(message = "all meta read", type = OperationType.READ)
    public List<ResponseImage> getAllMeta() {
        return imageMapper.toResponseList(imageRepository.findAll());
    }

    @Cacheable(value = "ImageService::getMeta", key = "#reference")
    @Audit(message = "meta read", type = OperationType.READ)
    public ResponseImage getMeta(String reference) {
        return imageMapper.toResponse(imageRepository.findByReference(reference)
                .orElseThrow(() -> new EntityNotFoundException("there is no file with such a reference")));
    }

    public byte[] download(String reference) throws Exception {
        return minioService.download(reference);
    }

}
