package com.nhnacademy.score.controller;

import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.domain.StudentRegisterRequest;
import com.nhnacademy.score.domain.StudentModifyRequest;
import com.nhnacademy.score.exception.StudentNotFoundException;
import com.nhnacademy.score.exception.ValidationFailedException;
import com.nhnacademy.score.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute("student")
    public Student getStudent(@PathVariable("studentId") Long studentId){
        Student student = studentRepository.getStudent(studentId);
        if (Objects.isNull(student)) {
            throw new StudentNotFoundException(studentId);
        }
        return student;
    }

    @GetMapping("/{studentId}")
    public String viewStudent() {
        return "studentView";
    }

    //TODO #2 studentController 작성
    @GetMapping("/{studentId}/modify")
    public String studentModifyForm(@ModelAttribute Student student, Model model) {
        model.addAttribute(student);
        return "studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public String modifyUser(@ModelAttribute Student student,
                             @Validated @ModelAttribute StudentModifyRequest postStudent,
                             BindingResult bindingResult,
                             ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }


        System.out.println("student = " + student);
        System.out.println("postStudent = " + postStudent);
        return "studentView";
    }

}
