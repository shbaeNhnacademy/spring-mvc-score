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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/students")
public class StudentRestController {
    private static final String STUDENT = "student";
    private final StudentRepository studentRepository;

    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute(STUDENT)
    public Student getStudent(@PathVariable("studentId") Long studentId){
        if (!studentRepository.exists(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        return studentRepository.getStudent(studentId);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> viewStudent(@ModelAttribute Student student) {
        return ResponseEntity.ok(student);
    }


    @PutMapping("/{studentId}")
    public ResponseEntity<Student> modifyStudent(@ModelAttribute Student student,
                             @Validated @ModelAttribute StudentModifyRequest postStudent,
                             BindingResult bindingResult,
                             ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long id = student.getId();
        Student modifyStudent = studentRepository.modifyStudent(
                id,postStudent.getName(),postStudent.getEmail(),postStudent.getScore(),postStudent.getComment());
        return ResponseEntity.ok(modifyStudent);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleStudentNotFoundException(StudentNotFoundException ex, Model model) {
        log.info("", ex);

        model.addAttribute("exception", ex);
        return "thymeleaf/error";
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
