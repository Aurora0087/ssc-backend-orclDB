package com.example.app1.post.videoPost;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class VideoUploadRequest {
    private String title;
    private String description;
    private String skill;
}
