package com.example.homework.dto.operation;

import lombok.Getter;
import lombok.Setter;
import com.example.homework.model.entity.mongo.enumeration.OperationType;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseOperation {

    private OperationType type;

    private String message;

    private LocalDateTime time;

}
