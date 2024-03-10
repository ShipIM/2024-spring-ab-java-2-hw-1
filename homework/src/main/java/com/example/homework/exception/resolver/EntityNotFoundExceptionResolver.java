package com.example.homework.exception.resolver;

import com.example.homework.exception.InvalidJwtException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityNotFoundExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private final Map<String, String> violationsMap;

    @Override
    protected GraphQLError resolveToSingleError(@NotNull Throwable ex,
                                                @NotNull DataFetchingEnvironment env) {
        GraphqlErrorBuilder<?> graphqlErrorBuilder = GraphqlErrorBuilder.newError()
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation());

        if (ex instanceof EntityNotFoundException) {
            return graphqlErrorBuilder
                    .message(ex.getMessage())
                    .errorType(ErrorType.NOT_FOUND)
                    .build();
        } else if (ex instanceof ConstraintViolationException) {
            return graphqlErrorBuilder
                    .message(ex.getMessage())
                    .errorType(ErrorType.BAD_REQUEST)
                    .build();
        } else if (ex instanceof DataIntegrityViolationException) {
            String message = violationsMap.entrySet().stream()
                    .filter(entry -> ex.getMessage().contains(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .findAny()
                    .orElse("An unexpected exception has occurred");

            return graphqlErrorBuilder
                    .message(message)
                    .errorType(ErrorType.BAD_REQUEST)
                    .build();
        } else if (ex instanceof InvalidJwtException) {
            return graphqlErrorBuilder
                    .message(ex.getMessage())
                    .errorType(ErrorType.UNAUTHORIZED)
                    .build();
        } else {
            return null;
        }
    }

}
