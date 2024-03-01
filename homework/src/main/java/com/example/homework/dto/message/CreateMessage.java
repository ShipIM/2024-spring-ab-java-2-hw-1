package com.example.homework.dto.message;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateMessage {

    private String text;

    private List<String> images;

}
