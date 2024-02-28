package com.example.homework.service;

import com.example.homework.dto.operation.filter.OperationFilter;
import com.example.homework.model.entity.mongo.QOperation;
import com.example.homework.repository.mongo.OperationRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import com.example.homework.model.entity.mongo.Operation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository repository;

    @CacheEvict(value = "OperationService::getOperations", allEntries = true)
    public void logOperation(Operation operation) {
        repository.save(operation);
    }

    @Cacheable(value = "OperationService::getOperations", key = "{ #filter?.type, #filter?.time }")
    public List<Operation> getOperations(OperationFilter filter) {
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.nonNull(filter)) {
            if (Objects.nonNull(filter.getType())) {
                builder.and(QOperation.operation.type.eq(filter.getType()));
            }

            if (Objects.nonNull(filter.getTime())) {
                builder.and(QOperation.operation.time.between(
                        filter.getTime().atStartOfDay(),
                        filter.getTime().plusDays(1).atStartOfDay()));
            }
        }

        List<Operation> operations = new ArrayList<>();
        repository.findAll(builder).forEach(operations::add);

        return operations;
    }

}
