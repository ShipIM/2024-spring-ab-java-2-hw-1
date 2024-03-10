package com.example.homework.service.impl;

import com.example.homework.annotation.Audit;
import com.example.homework.model.entity.jpa.RefreshToken;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.RefreshTokenRepository;
import com.example.homework.service.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @CacheEvict(value = "RefreshTokenService::getToken", allEntries = true)
    @Audit(message = "refresh token saved", type = OperationType.WRITE)
    public void setToken(User user, String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(new RefreshToken(null, user, null));
        refreshToken.setToken(token);

        refreshTokenRepository.save(refreshToken);
    }

    @Cacheable(value = "RefreshTokenService::getToken", key = "#user.id")
    @Audit(message = "refresh token read", type = OperationType.READ)
    public RefreshToken getToken(User user) {
        return refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("There is no token for this user"));
    }

    @CacheEvict(value = "RefreshTokenService::getToken", allEntries = true)
    @Audit(message = "refresh token deleted", type = OperationType.WRITE)
    @Transactional
    public void deleteToken(String refresh) {
        if (!refreshTokenRepository.existsByToken(refresh)) {
            throw new EntityNotFoundException("There is no such token");
        }

        refreshTokenRepository.deleteByToken(refresh);
    }

}
