package com.nhnacademy.score.controller;

import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class StudentRestControllerTest {
    private MockMvc mockMvc;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {

        studentRepository = mock(StudentRepository.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new StudentRestController(studentRepository)).build();
    }

    @Test
    void viewStudent_success() throws Exception {
        Long id = 1L;
        Student student = Student.create(id, "", "", 0, "");
        when(studentRepository.exists(anyLong())).thenReturn(true);
        when(studentRepository.getStudent(anyLong())).thenReturn(student);
        mockMvc.perform(get("/students/{studentId}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    void viewStudent_differentStudentId_fail() throws Exception {
        Long id = 1L;
        Student student = Student.create(id, "", "", 0, "");
        when(studentRepository.exists(anyLong())).thenReturn(true);
        when(studentRepository.getStudent(anyLong())).thenReturn(student);
        mockMvc.perform(get("/students/{studentId}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    void modifyStudent() {
    }

    @Test
    void registerStudent() {
    }

    @Test
    void handleStudentNotFoundException() {
    }

    @Test
    void handleValidationFailedException() {
    }
}
