package com.example.app1.post.videoPost;

import com.example.app1.user.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VideoContent {

    @SequenceGenerator(
            name = "video_content_sequence",
            sequenceName = "video_content_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "video_content_sequence"
    )
    @Id
    private Long id;
    private String title;
    private String description;
    private String skill;
    private Date postDate;
    private String videoUrl;
    private String thumbnailUrl;
    private Long likes;
    private Long dislikes;
    private Long views;
    private Boolean blocked;
    private Boolean free;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUser user;

    public VideoContent(String title, String description,String skill, String videoUrl, String thumbnailUrl, AppUser user, Boolean free) {
        this.title = title;
        this.description = description;
        this.skill = skill;
        this.postDate = new Date();
        this.videoUrl = videoUrl;
        this.thumbnailUrl =thumbnailUrl;
        this.likes= 0L;
        this.dislikes=0L;
        this.views=0L;
        this.user = user;
        this.blocked=false;
        this.free=free;
    }
}
