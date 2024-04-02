package com.example.homework.repository.jpa;

import com.example.homework.model.entity.jpa.RefreshToken;
import com.example.homework.model.entity.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    void deleteByToken(String token);

    boolean existsByToken(String token);

}
