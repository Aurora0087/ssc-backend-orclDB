package com.example.app1.post.videoPost.viewerResponse.likes;

import com.example.app1.post.videoPost.VideoContent;
import com.example.app1.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Likes {

    @SequenceGenerator(
            name = "likes_sequence",
            sequenceName = "likes_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "likes_sequence"
    )
    @Id
    private Long id;
    private Date likedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "v_id")
    private VideoContent videoContent;

    public Likes( AppUser user, VideoContent videoContent) {
        this.likedAt = new Date();
        this.user = user;
        this.videoContent = videoContent;
    }
}
