package com.example.homework.service;

import com.example.homework.model.entity.jpa.Image;
import com.example.homework.model.entity.mongo.Operation;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final OperationService operationService;

    private final ImageRepository imageRepository;

    @Cacheable(value = "ImageService::getMeta", key = "#image.reference")
    @CacheEvict(value = "ImageService::getAllMeta", allEntries = true)
    public Image saveMeta(Image image) {
        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Meta for %s saved to database", image.getReference()))
                        .time(LocalDateTime.now())
                        .type(OperationType.WRITE)
                        .build()
        );

        return imageRepository.save(image);
    }

    @Cacheable(value = "ImageService::getAllMeta")
    public List<Image> getAllMeta() {
        operationService.logOperation(
                Operation.builder()
                        .message("All meta read from database")
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );

        return imageRepository.findAll();
    }

    @Cacheable(value = "ImageService::getMeta", key = "#reference")
    public Image getMeta(String reference) {
        Image image = imageRepository.findByReference(reference)
                .orElseThrow(() -> new EntityNotFoundException("There is no file with such a reference"));

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Meta read for %s from database", reference))
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );

        return image;
    }

}
