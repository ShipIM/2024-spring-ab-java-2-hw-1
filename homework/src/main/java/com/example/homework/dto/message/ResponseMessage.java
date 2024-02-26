package com.example.homework.dto.message;

import com.example.homework.dto.image.ResponseImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseMessage {

    private Long id;

    private String author;

    private String text;

    private String creationDate;

    private List<ResponseImage> images;

}
