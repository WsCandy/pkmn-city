package com.gocity.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
            .errorType(get(ex))
            .message(ex.getMessage())
            .path(env.getExecutionStepInfo().getPath())
            .location(env.getField().getSourceLocation())
            .build();
    }

    private ErrorType get(Throwable ex) {
        if (ex instanceof StatusRuntimeException e) {
            return map(e.getStatus());
        }

        return ErrorType.INTERNAL_ERROR;
    }

    private ErrorType map(Status status) {
        return switch (status.getCode()) {
            case NOT_FOUND -> ErrorType.NOT_FOUND;
            case INVALID_ARGUMENT -> ErrorType.BAD_REQUEST;
            case PERMISSION_DENIED -> ErrorType.FORBIDDEN;
            case UNAUTHENTICATED -> ErrorType.UNAUTHORIZED;
            default -> ErrorType.INTERNAL_ERROR;
        };
    }
}
