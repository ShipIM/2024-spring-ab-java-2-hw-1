package com.example.homework.controller;

import com.example.homework.dto.mapper.UserMapper;
import com.example.homework.dto.user.AuthenticateUser;
import com.example.homework.dto.user.ResponseUser;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.service.AuthenticationService;
import com.example.homework.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @MutationMapping
    public ResponseUser register(@Argument @Valid AuthenticateUser details) {
        User user = userMapper.mapToUser(details);

        user = service.register(user);

        var jwtToken = jwtService.generateToken(user);

        ResponseUser authDto = userMapper.mapToResponse(user);
        authDto.setToken(jwtToken);

        return authDto;
    }

    @QueryMapping
    public ResponseUser authenticate(@Argument @Valid AuthenticateUser details) {
        User user = userMapper.mapToUser(details);

        user = service.authenticate(user);

        var jwtToken = jwtService.generateToken(user);

        ResponseUser authDto = userMapper.mapToResponse(user);
        authDto.setToken(jwtToken);

        return authDto;
    }

}
