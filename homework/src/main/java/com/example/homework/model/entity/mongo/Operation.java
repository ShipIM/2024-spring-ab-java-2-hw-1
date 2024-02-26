package com.example.homework.model.entity.mongo;

import jakarta.persistence.Id;
import lombok.*;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document("operations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Operation implements Serializable {

    @Id
    @Field("id")
    private String id;

    @Field("operation_type")
    private OperationType type;

    @Field("message")
    private String message;

    @Field("execution_time")
    private LocalDateTime time;

}
