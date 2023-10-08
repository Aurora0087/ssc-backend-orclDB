package com.example.app1.user;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private String firstName;
    private String lastName;
    private String profileImage;
}
