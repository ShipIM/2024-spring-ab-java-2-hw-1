package com.example.homework.service.impl;

import com.example.homework.annotation.Audit;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.UserRepository;
import com.example.homework.service.DetailsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailsServiceImpl implements DetailsService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "DetailsService::loadUserByUsername", key = "#username")
    @Audit(message = "user read", type = OperationType.READ)
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with that name"));
    }

    @Audit(message = "user saved", type = OperationType.WRITE)
    public User createUser(User user) {
        return userRepository.save(user);
    }

}
