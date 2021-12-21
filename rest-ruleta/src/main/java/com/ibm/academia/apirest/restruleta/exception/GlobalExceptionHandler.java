package com.ibm.academia.apirest.restruleta.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String NOT_READABLE_ERROR_MSG = "No se ingresaron datos o son incorrectos";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundHandler(NotFoundException e) {
        log.error(e.getMessage());
        ExceptionResponse response = ExceptionResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestHandler(BadRequestException e) {
        log.error(e.getMessage());
        ExceptionResponse response = ExceptionResponse.builder().message(e.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> notReadableBodyHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        ExceptionResponse response = ExceptionResponse.builder().message(NOT_READABLE_ERROR_MSG).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationHandler(ConstraintViolationException e) {
        log.error(e.getConstraintViolations().toString());
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(c ->
                errors.put(c.getPropertyPath().toString(), c.getMessage())
        );
        Map<String, Object> response = new HashMap<>();
        response.put("Message", NOT_READABLE_ERROR_MSG);
        response.put("Errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
