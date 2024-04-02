package com.example.homework.dto.operation.filter;

import lombok.Getter;
import lombok.Setter;
import com.example.homework.model.entity.mongo.enumeration.OperationType;

import java.time.LocalDate;

@Getter
@Setter
public class OperationFilter {

    private OperationType type;

    private LocalDate time;

}
