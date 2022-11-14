package com.nhnacademy.score.exception;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getAllErrors()
                           .stream()
                           .map(error -> new StringBuilder().append("ObjectName=").append(error.getObjectName()).append("\n")
                                                            .append(",Message=").append(error.getDefaultMessage()).append("\n")
                                                            .append(",code=").append(error.getCode()).append("\n"))
                           .collect(Collectors.joining(" | ")));
    }
}
