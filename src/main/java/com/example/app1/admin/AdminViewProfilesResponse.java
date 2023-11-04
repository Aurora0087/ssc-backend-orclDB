package com.example.app1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminViewProfilesResponse {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("profileImage")
    private String profileImage;
    @JsonProperty("role")
    private String role;
    @JsonProperty("lock")
    private Boolean lock;
    @JsonProperty("pro")
    private Boolean pro;
}
