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
    @JsonProperty("posted-time")
    private Date postedTime;
    @JsonProperty("video-id")
    private Long videoId;
    @JsonProperty("thumbnail-url")
    private String thumbnailUrl;
    @JsonProperty("profile")
    private UserProfile profile;
}
