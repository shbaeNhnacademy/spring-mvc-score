package com.nhnacademy.score.domain;

import lombok.Value;

import javax.validation.constraints.*;

@Value
public class StudentModifyRequest {
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
