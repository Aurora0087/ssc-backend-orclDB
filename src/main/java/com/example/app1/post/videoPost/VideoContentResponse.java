package com.example.app1.post.videoPost;

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
public class VideoContentResponse {
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("skill")
    private String skill;
    @JsonProperty("postedTime")
    private Date postedTime;
    @JsonProperty("videoUrl")
    private String videoUrl;
    @JsonProperty("profile")
    private UserProfile profile;
}
