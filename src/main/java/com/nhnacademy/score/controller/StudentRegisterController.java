package com.nhnacademy.score.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {

    @GetMapping
    public String studentRegisterForm() {
        return "thymeleaf/studentRegister";
    }

}
