package com.nhnacademy.score.exception;


public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(long id) {
        super("No." + id + "'s student can not found.");
    }
}
