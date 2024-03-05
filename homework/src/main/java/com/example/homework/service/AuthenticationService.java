package com.example.homework.service;

import com.example.homework.dto.jwt.ResponseJwt;
import com.example.homework.exception.InvalidJwtException;
import com.example.homework.model.entity.jpa.RefreshToken;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final DetailsService detailsService;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;

    public ResponseJwt register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleService.getRole("ROLE_USER")));

        user = detailsService.createUser(user);
        String access = generateAccessToken(user);
        String refresh = generateRefreshToken(user);

        return new ResponseJwt(access, refresh);
    }

    public ResponseJwt authenticate(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        user = (User) authentication.getPrincipal();
        String access = generateAccessToken(user);
        String refresh = generateRefreshToken(user);

        return new ResponseJwt(access, refresh);
    }

    public ResponseJwt getAccessToken(String refresh) {
        if (!jwtUtils.isRefreshTokenValid(refresh)) {
            throw new InvalidJwtException("Invalid refresh token");
        }

        String username = jwtUtils.extractRefreshUsername(refresh);
        User user = (User) detailsService.loadUserByUsername(username);
        RefreshToken savedRefresh = refreshTokenService.getToken(user);

        if (!savedRefresh.getToken().equals(refresh)) {
            throw new InvalidJwtException("Mismatch saved refresh token");
        }

        String access = generateAccessToken(user);

        return new ResponseJwt(access, refresh);
    }

    public ResponseJwt refreshToken(String refresh) {
        if (!jwtUtils.isRefreshTokenValid(refresh)) {
            throw new InvalidJwtException("Invalid refresh token");
        }

        String username = jwtUtils.extractRefreshUsername(refresh);
        User user = (User) detailsService.loadUserByUsername(username);
        RefreshToken savedRefresh = refreshTokenService.getToken(user);

        if (!savedRefresh.getToken().equals(refresh)) {
            throw new InvalidJwtException("Mismatch saved refresh token");
        }

        String updatedRefresh = generateRefreshToken(user);
        String updatedAccess = generateAccessToken(user);

        return new ResponseJwt(updatedAccess, updatedRefresh);
    }

    public void eraseRefreshToken(String refresh) {
        refreshTokenService.deleteToken(refresh);
    }

    private String generateAccessToken(User user) {
        return generateAccessToken(new HashMap<>(), user);
    }

    private String generateAccessToken(Map<String, Object> extraClaims, User user) {
        return jwtUtils.generateAccessToken(extraClaims, user);
    }

    private String generateRefreshToken(User user) {
        String token = jwtUtils.generateRefreshToken(user);
        refreshTokenService.setToken(user, token);

        return token;
    }

}
