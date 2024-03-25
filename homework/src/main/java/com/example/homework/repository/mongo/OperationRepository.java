package com.example.homework.repository.mongo;

import com.example.homework.model.entity.mongo.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends MongoRepository<Operation, Long>, QuerydslPredicateExecutor<Operation> {
}
