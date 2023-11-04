package com.example.app1.feedback;


import com.example.app1.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback {

    @SequenceGenerator(
            name = "feedback_sequence",
            sequenceName = "feedback_sequence",
            initialValue = 10,
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "feedback_sequence"
    )
    private Long id;
    private String title;
    private String details;
    private String type;
    private Date postTime;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private AppUser appUser;

    public Feedback(String title, String details, String type, AppUser user) {
        this.title = title;
        this.details = details;
        this.type = type;
        this.postTime=new Date();
        this.appUser=user;
    }
}
