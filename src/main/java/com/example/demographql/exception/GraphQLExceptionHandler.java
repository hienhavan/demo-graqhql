package com.example.demographql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Xử lý các ngoại lệ trong GraphQL và chuyển đổi thành GraphQLError
 */
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        // Xử lý lỗi validation
        if (ex instanceof ConstraintViolationException) {
            return ((ConstraintViolationException) ex).getConstraintViolations()
                    .stream()
                    .map(violation -> toGraphQLError(violation, env))
                    .collect(Collectors.toList());
        }
        
        // Xử lý các lỗi khác
        if (ex instanceof ValidationException) {
            return List.of(
                GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build()
            );
        }
        
        return null;
    }
    
    private GraphQLError toGraphQLError(ConstraintViolation<?> violation, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(violation.getPropertyPath() + ": " + violation.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }
}
