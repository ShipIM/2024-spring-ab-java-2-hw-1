package com.example.homework.dto.mapper;

import com.example.homework.dto.image.ResponseImage;
import com.example.homework.model.entity.jpa.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "filename", expression = "java(file.getOriginalFilename())")
    Image toImage(MultipartFile file, String reference);

    ResponseImage toResponse(Image image);

    Image toImage(String reference);

    List<ResponseImage> toResponseList(List<Image> images);

}
