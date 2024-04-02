package com.example.homework.dto.image;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseImage implements Serializable {

    private String filename;

    private Long size;

    private String reference;

}
