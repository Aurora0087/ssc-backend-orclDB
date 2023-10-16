package com.example.app1.post.videoPost;

import com.example.app1.user.AppUser;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class VideoContent {

    @SequenceGenerator(
            name = "video_content_sequence",
            sequenceName = "video_content_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "video_content_sequence"
    )
    private Long id;
    private String title;
    private String description;
    private String skill;
    private Date postDate;
    private String videoUrl;
    private Boolean blocked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public VideoContent(String title, String description,String skill, String videoUrl, AppUser user) {
        this.title = title;
        this.description = description;
        this.skill = skill;
        this.postDate = new Date();
        this.videoUrl = videoUrl;
        this.user = user;
        this.blocked=false;
    }
}
