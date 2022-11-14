package com.nhnacademy.score.domain;

import lombok.Value;

@Value
public class Student {
    // TODO #1 valid 설정
    private long id;
    private String name;
    private String email;
    private int score;
    private String comment;

}
