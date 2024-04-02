package com.example.homework.dto.operation.filter;

import com.example.homework.model.entity.mongo.enumeration.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OperationFilter {

    private OperationType type;

    private LocalDate time;

}
