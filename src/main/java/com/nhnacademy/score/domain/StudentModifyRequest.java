package com.nhnacademy.score.domain;

import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModifyRequest {
    @NotBlank
    private String name;
    @Email
    private String email;
    @Min(0)
    @Max(100)
    private Integer score;
    @NotBlank
    @Size(max = 200)
    private String comment;
}
