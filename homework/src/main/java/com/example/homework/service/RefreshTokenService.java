package com.example.homework.service;

import com.example.homework.model.entity.jpa.RefreshToken;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.model.entity.mongo.Operation;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final OperationService operationService;

    @CacheEvict(value = "RefreshTokenService::getToken", allEntries = true)
    public void setToken(User user, String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(new RefreshToken(null, user, null));
        refreshToken.setToken(token);

        refreshTokenRepository.save(refreshToken);

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Token set to database for user: %s", user.getUsername()))
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );
    }

    @Cacheable(value = "RefreshTokenService::getToken", key = "#user.id")
    public RefreshToken getToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("There is no token for this user"));

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Token read from database for user: %s", user.getUsername()))
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );

        return refreshToken;
    }

    @CacheEvict(value = "RefreshTokenService::getToken", allEntries = true)
    @Transactional
    public void deleteToken(String refresh) {
        if (!refreshTokenRepository.existsByToken(refresh)) {
            throw new EntityNotFoundException("There is no such token");
        }

        refreshTokenRepository.deleteByToken(refresh);

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Token deleted from database: %s", refresh))
                        .time(LocalDateTime.now())
                        .type(OperationType.WRITE)
                        .build()
        );
    }

}
