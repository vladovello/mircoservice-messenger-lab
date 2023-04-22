package com.messenger.friendsapp.presentation.controller.errorhandling;

import com.messenger.security.exception.UnauthorizedException;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    public RestResponseEntityExceptionHandler() {
        super();
    }

    // 400
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());

        var apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid data was send", ex.getMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(
            final @NonNull HttpMessageNotReadableException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull HttpStatus status,
            final @NonNull WebRequest request
    ) {
        logger.info(ex.getClass().getName());

        var apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid data was send", ex.getMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    // 401
    @ExceptionHandler({UnauthorizedException.class})
    public @NonNull ResponseEntity<Object> handleUnauthorized(
            final @NonNull MethodArgumentNotValidException ex,
            final @NonNull HttpHeaders headers,
            final @NonNull WebRequest request
    ) {
        logger.info("request" + request.getUserPrincipal());

        var apiError = new ApiError(HttpStatus.UNAUTHORIZED, "It's not allowed for you to perform this action", ex.getMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }


    // 403
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            final Exception ex,
            final HttpHeaders headers,
            final @NonNull WebRequest request
    ) {
        logger.info("request" + request.getUserPrincipal());

        var apiError = new ApiError(HttpStatus.FORBIDDEN, "It's not allowed to perform this action", ex.getMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    // 409
    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class})
    protected ResponseEntity<Object> handleConflict(
            final RuntimeException ex,
            final HttpHeaders headers,
            final WebRequest request
    ) {
        logger.info(ex.getClass().getName() + ex.getMessage());

        var apiError = new ApiError(HttpStatus.CONFLICT, "There is a conflicting data", ex.getMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    // 412

    // 500
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);

        var apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong on the server",
                ex.getMessage()
        );
        return handleExceptionInternal(
                ex,
                apiError,
                new HttpHeaders(),
                apiError.getStatus(),
                request
        );
    }
}
