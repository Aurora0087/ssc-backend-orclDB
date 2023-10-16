package com.example.app1.updateProfile;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmailChangeRequest {
    private String oldEmail;
    private String newEmail;
}
