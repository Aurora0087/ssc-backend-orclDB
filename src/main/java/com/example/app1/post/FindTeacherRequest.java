package com.example.app1.post;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FindTeacherRequest {
    private final String heading;
    private final String skill;
    private final String description;
    private final String location;
}
