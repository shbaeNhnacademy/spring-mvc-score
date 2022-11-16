package com.nhnacademy.score.controller;

import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.domain.StudentRegisterRequest;
import com.nhnacademy.score.exception.ValidationFailedException;
import com.nhnacademy.score.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/register")
public class StudentRegisterRestController {
    private final StudentRepository studentRepository;

    public StudentRegisterRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@Validated @ModelAttribute StudentRegisterRequest studentRegisterRequest,
                                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        Student register = studentRepository.register(
                studentRegisterRequest.getName(), studentRegisterRequest.getEmail(), studentRegisterRequest.getScore(), studentRegisterRequest.getComment());
        return ResponseEntity.ok(register);
    }
}
