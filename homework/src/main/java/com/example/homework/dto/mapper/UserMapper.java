package com.example.homework.dto.mapper;

import com.example.homework.dto.user.AuthenticateUser;
import com.example.homework.model.entity.jpa.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.security.Principal;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToUser(AuthenticateUser dto);

    @Mapping(target = "username", expression = "java(principal.getName())")
    User mapToUser(Principal principal);

}
