package com.nhnacademy.score.repository;


import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.exception.StudentAlreadyExistsException;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class StudentRepositoryImpl implements StudentRepository {

    private final Map<Long, Student> studentMap = new ConcurrentHashMap<>();

    @Override
    public boolean exists(long id) {
        return studentMap.containsKey(id);
    }

    @Override
    public Student register(String name, String email, int score, String comment) {
        Long id = studentMap.keySet()
                .stream()
                .max(Comparator.comparing(Function.identity())) //l->l
                .map(l -> l + 1)
                .orElse(1L);

        if (exists(id)) {
            throw new StudentAlreadyExistsException();
        }

        Student student = Student.create(id, name, email, score, comment);
        studentMap.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(long id) {
        return studentMap.get(id);
    }

    @Override
    public Student modifyStudent(long id, String name, String email, int score, String comment) {
        Student newStudent = Student.create(id, name, email, score, comment);
        studentMap.put(id, newStudent);
        return newStudent;
    }
}
