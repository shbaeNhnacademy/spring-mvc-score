package com.nhnacademy.score.domain;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.*;

@Value
public class Student {
    // TODO #1 valid 설정
    @NonNull
    private long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @Min(0)
    @Max(100)
    private int score;
    @NotBlank
    @Size(max = 200)
    private String comment;


}
