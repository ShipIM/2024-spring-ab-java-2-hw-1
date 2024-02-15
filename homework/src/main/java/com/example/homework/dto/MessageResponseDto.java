package com.example.homework.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDto {

    private Long id;
    private String author;
    private String text;

}
