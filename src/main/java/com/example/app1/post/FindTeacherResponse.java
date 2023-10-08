package com.example.app1.post;

import com.example.app1.user.UserProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindTeacherResponse {
    @JsonProperty("header")
    private String header;
    @JsonProperty("skill")
    private String skill;
    @JsonProperty("description")
    private String description;
    @JsonProperty("location")
    private String location;
    @JsonProperty("postedTime")
    private Date postedTime;
    @JsonProperty("profile")
    private UserProfile profile;
}
