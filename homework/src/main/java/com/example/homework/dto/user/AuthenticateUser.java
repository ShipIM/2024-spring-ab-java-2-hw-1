package com.example.homework.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateUser {

    @Size(min = 1, max = 16, message = "incorrect username size")
    @NotBlank(message = "username must not be empty")
    private String username;

    @Size(min = 8, max = 16, message = "incorrect password size")
    @NotBlank(message = "password must not be empty")
    private String password;

}
