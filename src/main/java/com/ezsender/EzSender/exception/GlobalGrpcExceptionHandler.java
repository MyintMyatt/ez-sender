package com.ezsender.EzSender.exception;

import io.grpc.Status;
import io.grpc.StatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/// This exception handler is for gRPC server side not for client side
@Slf4j
@Component
public class GlobalGrpcExceptionHandler implements GrpcExceptionHandler {
    @Override
    public StatusException handleException(Throwable ex) {
        log.error("gRPC Error: {}", ex.getMessage(), ex);
        if (ex instanceof  AuthenticationException b) {
            return Status
                    .UNAUTHENTICATED
                    .withCause(ex)
                    .withDescription(b.getMessage())
                    .asException();
        } else if (ex instanceof  RuntimeException b) {
            return Status
                    .INTERNAL
                    .withCause(ex)
                    .withDescription(b.getMessage())
                    .asException();
        } else {
            return Status
                    .INTERNAL
                    .withCause(ex)
                    .withDescription(ex.getMessage())
                    .asException();
        }

    }
}
