package com.example.app1.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepo extends JpaRepository<Feedback,Long> {
}
