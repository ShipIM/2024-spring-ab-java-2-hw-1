package com.example.homework.controller;

import com.example.homework.dto.image.ResponseImage;
import com.example.homework.service.ImageService;
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

    @PostMapping
    public ResponseImage upload(@Valid @NotNull(message = "file must not be empty") MultipartFile file) {
        return imageService.saveImage(file);
    }

    @GetMapping("/{reference}")
    public byte[] download(@PathVariable String reference) {
        return imageService.download(reference);
    }

}
