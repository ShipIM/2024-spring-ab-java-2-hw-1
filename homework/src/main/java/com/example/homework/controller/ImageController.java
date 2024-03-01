package com.example.homework.controller;

import com.example.homework.dto.image.ResponseImage;
import com.example.homework.dto.mapper.ImageMapper;
import com.example.homework.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;
    private final ImageMapper mapper;

    @QueryMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ResponseImage> getMetas() {
        return mapper.toResponseList(service.getAllMeta());
    }

    @QueryMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseImage getMeta(@Argument String reference) {
        return mapper.toResponse(service.getMeta(reference));
    }

}
