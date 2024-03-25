package com.example.homework.controller;

import com.example.homework.dto.image.ResponseImage;
import com.example.homework.dto.mapper.ImageMapper;
import com.example.homework.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;

    @QueryMapping
    public List<ResponseImage> getMetas() {
        return service.getAllMeta();
    }

    @QueryMapping
    public ResponseImage getMeta(@Argument String reference) {
        return service.getMeta(reference);
    }

}
