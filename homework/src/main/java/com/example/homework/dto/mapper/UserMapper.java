package com.example.homework.dto.mapper;

import com.example.homework.dto.user.AuthenticateUser;
import com.example.homework.model.entity.jpa.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToUser(AuthenticateUser dto);

}
