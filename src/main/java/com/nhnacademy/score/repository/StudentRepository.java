package com.nhnacademy.score.repository;


import com.nhnacademy.score.domain.Student;

public interface StudentRepository {
    boolean exists(long id);

    Student register(String name, String email, int score, String comment);

    Student getStudent(long id);

    Student modifyStudent(long id, String name, String email, int score, String comment);

}
