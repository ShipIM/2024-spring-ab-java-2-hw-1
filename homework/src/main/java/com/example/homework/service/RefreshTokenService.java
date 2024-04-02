package com.example.homework.service;

import com.example.homework.model.entity.jpa.RefreshToken;
import com.example.homework.model.entity.jpa.User;

public interface RefreshTokenService {

    void setToken(User user, String token);

    RefreshToken getToken(User user);

    void deleteToken(String refresh);

}
