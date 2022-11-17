package com.nhnacademy.score.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.domain.StudentModifyRequest;
import com.nhnacademy.score.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    void viewStudent_differentStudentId_fail() throws Exception {
        Long id = 1L;
        Student student = Student.create(id, "", "", 0, "");
        when(studentRepository.exists(anyLong())).thenReturn(false);
        when(studentRepository.getStudent(anyLong())).thenReturn(student);
        mockMvc.perform(get("/students/{studentId}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(studentRepository, times(1)).exists(anyLong());
        verify(studentRepository, never()).getStudent(anyLong());

    }

    @Test
    void modifyStudent_success() throws Exception {
        long id =1L;
        String name = "aaa";
        String email = "2@1.com";
        int score = 10;
        String comment = "222";

        Student preStudent = Student.create(1L, "", "", 0, "");
        Student postStudent = Student.create(1L, name, email, score, comment);
        StudentModifyRequest studentModifyRequest = new StudentModifyRequest();
        studentModifyRequest.setName(name);
        studentModifyRequest.setEmail(email);
        studentModifyRequest.setScore(score);
        studentModifyRequest.setComment(comment);

        when(studentRepository.exists(anyLong())).thenReturn(true);
        when(studentRepository.getStudent(anyLong())).thenReturn(preStudent);
        when(studentRepository.modifyStudent(id, name, email, score, comment)).thenReturn(postStudent);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(studentModifyRequest);

        mockMvc.perform(put("/students/{studentId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(score))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(comment));

    }

    @Test
    void modifyStudent_invalidParam_fail() throws Exception {
        long id =1L;
        String name = "";
        String email = "2@1";
        int score = 1000;
        String comment = "";

        Student preStudent = Student.create(1L, "", "", 0, "");
        Student postStudent = Student.create(1L, name, email, score, comment);
        StudentModifyRequest studentModifyRequest = new StudentModifyRequest();
        studentModifyRequest.setName(name);
        studentModifyRequest.setEmail(email);
        studentModifyRequest.setScore(score);
        studentModifyRequest.setComment(comment);

        when(studentRepository.exists(anyLong())).thenReturn(true);
        when(studentRepository.getStudent(anyLong())).thenReturn(preStudent);
        when(studentRepository.modifyStudent(id, name, email, score, comment)).thenReturn(postStudent);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(studentModifyRequest);

        mockMvc.perform(put("/students/{studentId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(score))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value(comment));

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
