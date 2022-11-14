package com.nhnacademy.score.repository;


import com.nhnacademy.score.domain.Student;
import com.nhnacademy.score.exception.StudentAlreadyExistsException;
import com.nhnacademy.score.exception.StudentNotFoundException;

import java.util.Comparator;
import java.util.HashMap;
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
                .max(Comparator.comparing(Function.identity()))
                .map(l -> l + 1)
                .orElse(1L);

        //TODO #4 이거 해야하나?
        if (exists(id)) {
            throw new StudentAlreadyExistsException();
        }

        Student student = new Student(id, name, email, score, comment);
        studentMap.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(long id) {
        if (!exists(id)) {
            throw new StudentNotFoundException(id);
        }
        return studentMap.get(id);
    }
}
