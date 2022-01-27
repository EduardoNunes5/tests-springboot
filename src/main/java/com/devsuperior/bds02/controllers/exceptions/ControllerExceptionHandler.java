package com.devsuperior.bds02.controllers.exceptions;

import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest req){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = buildError(e.getMessage(), req.getRequestURI(), status, "Resource not found");
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest req){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = buildError(e.getMessage(), req.getRequestURI(), status, "Database Exception.");
        return ResponseEntity.status(status).body(error);
    }

    private StandardError buildError(String msg, String path, HttpStatus status, String errorStr){
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setMessage(msg);
        error.setPath(path);
        error.setStatus(status.value());
        error.setError(errorStr);
        return error;
    }

}
