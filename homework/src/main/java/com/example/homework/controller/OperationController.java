package com.example.homework.controller;

import com.example.homework.dto.mapper.OperationMapper;
import com.example.homework.dto.operation.ResponseOperation;
import com.example.homework.dto.operation.filter.OperationFilter;
import com.example.homework.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OperationController {

    private final OperationService service;
    private final OperationMapper mapper;

    @QueryMapping
    public List<ResponseOperation> getOperations(@Argument OperationFilter filter) {
        return mapper.toResponseList(service.getOperations(filter));
    }

}
