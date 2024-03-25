package com.example.homework.dto.mapper;

import com.example.homework.dto.operation.ResponseOperation;
import com.example.homework.model.entity.mongo.Operation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    ResponseOperation toResponse(Operation operation);

    List<ResponseOperation> toResponseList(List<Operation> operations);

}
