package com.yte.intern.project.security.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private List<ErrorModel> errorMessage;

    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {

        List<ErrorModel> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());
        return ErrorResponse.builder().errorMessage(errorMessages).build();
    }

    @ExceptionHandler(value= ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ConstraintViolationException exception) {

        String errorMessage = exception.getMessage();
        List<ErrorModel> errorModelList = new ArrayList<>();

        errorModelList.add(new ErrorModel("", null, errorMessage));

        return ErrorResponse.builder().errorMessage(errorModelList).build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflict(DataIntegrityViolationException e) {

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        ErrorModel errorMessage = new ErrorModel("",null,message);
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorModel{
    private String fieldName;
    private Object rejectedValue;
    private String messageError;
}
