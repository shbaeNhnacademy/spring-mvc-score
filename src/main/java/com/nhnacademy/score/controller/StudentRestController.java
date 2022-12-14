package com.nhnacademy.score.controller;

import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.domain.StudentModifyRequest;
import com.nhnacademy.score.domain.StudentRegisterRequest;
import com.nhnacademy.score.exception.StudentNotFoundException;
import com.nhnacademy.score.exception.ValidationFailedException;
import com.nhnacademy.score.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/students")
public class StudentRestController {
    private final StudentRepository studentRepository;

    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> viewStudent(@PathVariable(value = "studentId") Long studentId) {
        if (!studentRepository.exists(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        return ResponseEntity.ok(studentRepository.getStudent(studentId));
    }


    @PutMapping("/{studentId}")
    public ResponseEntity<Student> modifyStudent(@PathVariable(value = "studentId") Long studentId,
                             @Validated @RequestBody StudentModifyRequest studentModifyRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        if (!studentRepository.exists(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        Student student = studentRepository.getStudent(studentId);

        Long id = student.getId();
        Student modifyStudent = studentRepository.modifyStudent(
                id, studentModifyRequest.getName(), studentModifyRequest.getEmail(), studentModifyRequest.getScore(), studentModifyRequest.getComment());
        return ResponseEntity.ok(modifyStudent);
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@Validated @RequestBody StudentRegisterRequest studentRegisterRequest,
                                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        Student register = studentRepository.register(
                studentRegisterRequest.getName(), studentRegisterRequest.getEmail(), studentRegisterRequest.getScore(), studentRegisterRequest.getComment());
        return ResponseEntity.created(URI.create("/students/"+register.getId())).build();
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<String> handleValidationFailedException(ValidationFailedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
