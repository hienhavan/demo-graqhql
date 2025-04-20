package com.example.demographql.exception;

/**
 * Exception được ném ra khi có lỗi validation
 */
public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
