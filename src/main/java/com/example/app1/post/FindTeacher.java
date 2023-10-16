package com.example.app1.post;

import com.example.app1.user.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class FindTeacher {

    @SequenceGenerator(
            name = "find_teacher_sequence",
            sequenceName = "find_teacher_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "find_teacher_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String heading;
    @Column(nullable = false)
    private String skill;
    private String description;
    private String location;
    private Date postedTime;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUser appUser;

    public FindTeacher(String heading, String skill, String description, String location, Date postedTime, AppUser appUser) {
        this.heading = heading;
        this.skill = skill;
        this.description = description;
        this.location = location;
        this.postedTime = postedTime;
        this.appUser = appUser;
    }
}
