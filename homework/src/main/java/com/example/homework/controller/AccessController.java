package com.example.homework.controller;

import com.example.homework.dto.image.ResponseImage;
import com.example.homework.dto.mapper.ImageMapper;
import com.example.homework.model.entity.jpa.Image;
import com.example.homework.service.ImageService;
import com.example.homework.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class AccessController {

    private final ImageService imageService;
    private final MinioService minioService;
    private final ImageMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseImage upload(MultipartFile file) throws Exception {
        String reference = minioService.upload(file);
        Image image = mapper.toImage(file);
        image.setReference(reference);

        image = imageService.saveMeta(image);

        return mapper.toResponse(image);
    }

    @GetMapping("/{reference}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public byte[] download(@PathVariable String reference) throws Exception {
        return minioService.download(reference);
    }

}
