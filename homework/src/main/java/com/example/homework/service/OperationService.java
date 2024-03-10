package com.example.homework.service;

import com.example.homework.dto.operation.filter.OperationFilter;
import com.example.homework.model.entity.mongo.Operation;

import java.util.List;

public interface OperationService {

    void logOperation(Operation operation);

    List<Operation> getOperations(OperationFilter filter);

}
