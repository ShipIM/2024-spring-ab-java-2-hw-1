package com.example.homework.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateUser {

    @Size(min = 1, max = 16, message = "Неправильный размер login")
    @NotBlank(message = "login не должен быть пустым")
    private String username;

    @Size(min = 8, max = 16, message = "Неправильный размер пароля")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

}
