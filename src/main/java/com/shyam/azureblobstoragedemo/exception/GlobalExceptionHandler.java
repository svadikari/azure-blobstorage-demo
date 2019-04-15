package com.shyam.azureblobstoragedemo.exception;

import com.shyam.azureblobstoragedemo.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle(MethodArgumentNotValidException e) {
        Set<String> messages = new HashSet<>(e.getBindingResult().getFieldErrors().size());
        messages.addAll(e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s='%s' : %s", capitalizeFirstCharachter(fieldError.getField()),
                        fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList()));
        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        messages.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity handle(InvalidInputDataException invalidInputDataExp) {
        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        invalidInputDataExp.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleMethodNotSupportedException(Exception e) {
        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
                        e.getMessage()),
                HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler({OrderServiceException.class, Exception.class})
    public ResponseEntity handle(Exception e) {
        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s = '%s' : %s",
                        getFieldName(constraintViolation.getPropertyPath()),
                        constraintViolation.getInvalidValue(),
                        constraintViolation.getMessage()))
                .collect(Collectors.toList()));

        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        messages.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handle(NotFoundException e) {
        return new ResponseEntity<>(
                new ErrorModel(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    private String getFieldName(Path propertyPath) {
        String fieldName = propertyPath.toString().substring(propertyPath.toString().lastIndexOf('.') + 1);
        return capitalizeFirstCharachter(fieldName);
    }

    private String capitalizeFirstCharachter(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


}
