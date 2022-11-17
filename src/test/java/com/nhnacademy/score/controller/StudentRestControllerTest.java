package com.nhnacademy.score.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.domain.StudentModifyRequest;
import com.nhnacademy.score.domain.StudentRegisterRequest;
import com.nhnacademy.score.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        verify(studentRepository, times(1)).exists(anyLong());
        verify(studentRepository, times(1)).getStudent(anyLong());
    }

    @Test
    void modifyStudent_invalidParam_fail() throws Exception {
        long id =1L;
        String name = "saiwdnqiwndisnad";
        String email = "2@1";
        int score = 1000;
        String comment = "wqfqwf";

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
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(studentRepository, never()).exists(anyLong());
        verify(studentRepository, never()).getStudent(anyLong());
    }

    @Test
    void modifyStudent_studentNotFound_fail() throws Exception {
        long id =1L;
        String name = "saiwdnqiwndisnad";
        String email = "2@1.com";
        int score = 100;
        String comment = "wqfqwf";

        Student preStudent = Student.create(id, "", "", 0, "");
        Student postStudent = Student.create(2L, name, email, score, comment);
        StudentModifyRequest studentModifyRequest = new StudentModifyRequest();
        studentModifyRequest.setName(name);
        studentModifyRequest.setEmail(email);
        studentModifyRequest.setScore(score);
        studentModifyRequest.setComment(comment);

        when(studentRepository.exists(anyLong())).thenReturn(false);
        when(studentRepository.getStudent(anyLong())).thenReturn(preStudent);
        when(studentRepository.modifyStudent(id, name, email, score, comment)).thenReturn(postStudent);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(studentModifyRequest);

        mockMvc.perform(put("/students/{studentId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(studentRepository, times(1)).exists(anyLong());
        verify(studentRepository, never()).getStudent(anyLong());
    }

    @Test
    void registerStudent_success() throws Exception {
        long id =1L;
        String name = "aaa";
        String email = "2@1.com";
        int score = 100;
        String comment = "222";

        Student student = Student.create(1L, name, email, score, comment);

        StudentRegisterRequest studentRegisterRequest = new StudentRegisterRequest();
        studentRegisterRequest.setName(name);
        studentRegisterRequest.setEmail(email);
        studentRegisterRequest.setScore(score);
        studentRegisterRequest.setComment(comment);

        when(studentRepository.register(name, email, score, comment)).thenReturn(student);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(studentRegisterRequest);

        MvcResult mvcResult = mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("Location")).isEqualTo("/students/1");
        verify(studentRepository, times(1)).register(anyString(), anyString(), anyInt(), anyString());
    }

    @Test
    void registerStudent_invalidParam_fail() throws Exception {
        long id =1L;
        String name = "aaa";
        String email = "2@1.com";
        int score = 1000;
        String comment = "222";

        Student student = Student.create(1L, name, email, score, comment);

        StudentRegisterRequest studentRegisterRequest = new StudentRegisterRequest();
        studentRegisterRequest.setName(name);
        studentRegisterRequest.setEmail(email);
        studentRegisterRequest.setScore(score);
        studentRegisterRequest.setComment(comment);

        when(studentRepository.register(name, email, score, comment)).thenReturn(student);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(studentRegisterRequest);

       mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(studentRepository, never()).register(anyString(), anyString(), anyInt(), anyString());

    }

}
