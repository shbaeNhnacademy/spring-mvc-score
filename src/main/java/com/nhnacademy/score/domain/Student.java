package com.nhnacademy.score.domain;

import javax.validation.constraints.*;

public class Student {
    private final Long id;
    private final String name;
    private final String email;
    private final int score;
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
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
