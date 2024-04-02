package com.example.homework.service;

import com.example.homework.dto.jwt.ResponseJwt;
import com.example.homework.model.entity.jpa.User;

public interface AuthenticationService {

    ResponseJwt register(User user);

    ResponseJwt authenticate(User user);

    ResponseJwt getAccessToken(String refresh);

    ResponseJwt refreshToken(String refresh);

    void eraseRefreshToken(String refresh);

}
