package com.example.homework.service.impl;

import com.example.homework.annotation.Audit;
import com.example.homework.model.entity.jpa.Image;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.ImageRepository;
import com.example.homework.service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Cacheable(value = "ImageService::getMeta", key = "#image.reference")
    @CacheEvict(value = "ImageService::getAllMeta", allEntries = true)
    @Audit(message = "meta saved", type = OperationType.WRITE)
    public Image saveMeta(Image image) {
        return imageRepository.save(image);
    }

    @Cacheable(value = "ImageService::getAllMeta")
    @Audit(message = "all meta read", type = OperationType.READ)
    public List<Image> getAllMeta() {
        return imageRepository.findAll();
    }

    @Cacheable(value = "ImageService::getMeta", key = "#reference")
    @Audit(message = "meta read", type = OperationType.READ)
    public Image getMeta(String reference) {
        return imageRepository.findByReference(reference)
                .orElseThrow(() -> new EntityNotFoundException("There is no file with such a reference"));
    }

}
