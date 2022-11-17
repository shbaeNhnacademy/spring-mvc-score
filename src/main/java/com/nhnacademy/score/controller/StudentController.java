package com.nhnacademy.score.controller;

import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.domain.StudentModifyRequest;
import com.nhnacademy.score.exception.StudentNotFoundException;
import com.nhnacademy.score.exception.ValidationFailedException;
import com.nhnacademy.score.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    private static final String STUDENT = "student";
    private static final String THYMELEAF_STUDENT_VIEW = "thymeleaf/studentView";
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute(STUDENT)
    public Student getStudent(@PathVariable("studentId") Long studentId){
        if (!studentRepository.exists(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        Student student = studentRepository.getStudent(studentId);
        return student;
    }

    @GetMapping(path = "/{studentId}")
    public String viewStudent() {
        return THYMELEAF_STUDENT_VIEW;
    }

    @GetMapping(path = "/{studentId}",params = {"hideScore=yes"})
    public String viewStudentWithoutScoreAndComment(@ModelAttribute Student student, Model model) {
        model.addAttribute(STUDENT,
                new Student(student.getId(), student.getName(), student.getEmail(), -1, ""));
        return THYMELEAF_STUDENT_VIEW;
    }

    @GetMapping("/{studentId}/modify")
    public String studentModifyForm(@ModelAttribute Student student, Model model) {
        model.addAttribute(student);
        return "thymeleaf/studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public String modifyStudent(@ModelAttribute Student student,
                             @Validated @ModelAttribute StudentModifyRequest postStudent,
                             BindingResult bindingResult,
                             ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Long id = student.getId();
        Student modifyStudent = studentRepository.modifyStudent(
                id,postStudent.getName(),postStudent.getEmail(),postStudent.getScore(),postStudent.getComment());

        modelMap.put(STUDENT, modifyStudent);
        return THYMELEAF_STUDENT_VIEW;
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleStudentNotFoundException(StudentNotFoundException ex, Model model) {
        log.info("", ex);

        model.addAttribute("exception", ex);
        return "thymeleaf/error";
    }


}
