package com.nhnacademy.score.domain;

import lombok.Getter;

import javax.validation.constraints.*;

public class Student {
    @Getter
    private final Long id;
    @Getter
    private final String name;
    @Getter
    private final String email;
    @Getter
    private final int score;
    @Getter
    private final String comment;

    public Student(Long id, String name, String email, int score, String comment) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.score = score;
        this.comment = comment;
    }

    public static Student create(Long id, String name, String email, int score, String comment) {
        return new Student(id, name, email, score, comment);
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}
