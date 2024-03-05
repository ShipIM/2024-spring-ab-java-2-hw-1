package com.example.homework.service;

import com.example.homework.model.entity.jpa.User;
import com.example.homework.model.entity.mongo.Operation;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DetailsService implements UserDetailsService {

    private final OperationService operationService;
    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "DetailsService::loadUserByUsername", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with that name"));

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("User read from database: %s", username))
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );

        return user;
    }

    public User createUser(User user) {
        User saved = userRepository.save(user);

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("User saved to database: %s", user.getUsername()))
                        .time(LocalDateTime.now())
                        .type(OperationType.WRITE)
                        .build()
        );

        return saved;
    }

}
