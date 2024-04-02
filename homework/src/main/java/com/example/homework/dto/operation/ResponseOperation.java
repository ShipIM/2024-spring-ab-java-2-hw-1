package com.example.homework.dto.operation;

import com.example.homework.model.entity.mongo.enumeration.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseOperation {

    private OperationType type;

    private String message;

    private LocalDateTime time;

}
