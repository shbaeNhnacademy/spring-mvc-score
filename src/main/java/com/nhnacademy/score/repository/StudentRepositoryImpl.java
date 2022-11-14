package com.nhnacademy.score.repository;


import com.nhnacademy.score.domain.Student;

public class StudentRepositoryImpl implements StudentRepository {
    @Override
    public boolean exists(long id) {
        return false;
    }

    @Override
    public Student register(String name, String email, int score, String comment) {
        return null;
    }

    @Override
    public Student getStudent(long id) {
        return null;
    }
}
