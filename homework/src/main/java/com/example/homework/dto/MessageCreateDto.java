package com.example.homework.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageCreateDto {

    @NotBlank(message = "Должен быть указан автор сообщения")
    private String author;

    @NotBlank(message = "Сообщение должно содержать текст")
    private String text;

}
