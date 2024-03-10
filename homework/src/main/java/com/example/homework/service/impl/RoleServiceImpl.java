package com.example.homework.service.impl;

import com.example.homework.annotation.Audit;
import com.example.homework.model.entity.jpa.Role;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.RoleRepository;
import com.example.homework.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Cacheable(value = "RoleService::getRole", key = "#name")
    @Audit(message = "role read", type = OperationType.READ)
    public Role getRole(String name) {
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new EntityNotFoundException("There is no role with such name"));
    }

}
