package com.example.homework.service;

import com.example.homework.model.entity.jpa.Role;
import com.example.homework.model.entity.mongo.Operation;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final OperationService operationService;
    private final RoleRepository roleRepository;

    @Cacheable(value = "RoleService::getRole", key = "#name")
    public Role getRole(String name) {
        Role role = roleRepository.findRoleByName(name)
                .orElseThrow(() -> new EntityNotFoundException("There is no role with such name"));

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Role read from database: %s", name))
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );

        return role;
    }

}
