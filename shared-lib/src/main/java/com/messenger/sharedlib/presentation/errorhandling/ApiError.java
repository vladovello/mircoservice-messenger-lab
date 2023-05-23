package com.messenger.sharedlib.presentation.errorhandling;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(final HttpStatus status, final String message, final List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(final HttpStatus status, final String message, final String error) {
        this.status = status;
        this.message = message;
        errors = List.of(error);
    }

    public ApiError(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
        errors = List.of(status.getReasonPhrase());
    }

    public void setError(final String error) {
        errors = List.of(error);
    }
}
