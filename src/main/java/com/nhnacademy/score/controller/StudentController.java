package com.nhnacademy.score.controller;

import com.nhnacademy.score.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{studentId}")
    public String viewStudent() {
        return "studentView";
    }

    @GetMapping("/{studentId}/modify")
    public String studentModifyForm() {
        return "studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public String modifyUser() {
        return "studentView";
    }

}
