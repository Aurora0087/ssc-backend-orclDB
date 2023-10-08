package com.example.app1.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface FindTeacherRepo extends JpaRepository<FindTeacher, Long> {
    Optional<FindTeacher> findByLocation(String location);
}
