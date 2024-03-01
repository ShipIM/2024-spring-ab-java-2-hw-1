package com.example.homework.dto.user;

import com.example.homework.model.entity.jpa.enumeration.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUser {

    private String username;

    private String token;

    private Role role;

}
