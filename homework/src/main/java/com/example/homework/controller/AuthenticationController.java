package com.example.homework.controller;

import com.example.homework.dto.jwt.ResponseJwt;
import com.example.homework.dto.mapper.UserMapper;
import com.example.homework.dto.user.AuthenticateUser;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.service.AuthenticationService;
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
    private final UserMapper userMapper;

    @MutationMapping
    public ResponseJwt register(@Argument @Valid AuthenticateUser details) {
        User user = userMapper.mapToUser(details);

        return service.register(user);
    }

    @QueryMapping
    public ResponseJwt authenticate(@Argument @Valid AuthenticateUser details) {
        User user = userMapper.mapToUser(details);

        return service.authenticate(user);
    }

    @MutationMapping
    public ResponseJwt access(@Argument String refresh) {
        return service.getAccessToken(refresh);
    }

    @MutationMapping
    public ResponseJwt refresh(@Argument String refresh) {
        return service.refreshToken(refresh);
    }

    @MutationMapping
    public boolean eraseRefresh(@Argument String refresh) {
        service.eraseRefreshToken(refresh);

        return true;
    }

}
