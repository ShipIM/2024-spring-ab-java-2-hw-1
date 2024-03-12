package com.example.homework.controller;

import com.example.homework.dto.image.ResponseImage;
import com.example.homework.dto.mapper.ImageMapper;
import com.example.homework.model.entity.jpa.Image;
import com.example.homework.service.ImageService;
import com.example.homework.service.MinioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
    public ResponseImage upload(
            @Valid
            @NotNull(message = "file must not be empty")
            MultipartFile file
    ) throws Exception {
        String reference = minioService.upload(file);
        Image image = mapper.toImage(file, reference);

        image = imageService.saveMeta(image);

        return mapper.toResponse(image);
    }

    @GetMapping("/{reference}")
    public byte[] download(@PathVariable String reference) throws Exception {
        return minioService.download(reference);
    }

}
