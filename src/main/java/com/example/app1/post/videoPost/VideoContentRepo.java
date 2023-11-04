package com.example.app1.post.videoPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoContentRepo extends JpaRepository<VideoContent,Long> {

    List<VideoContent> findByBlockedFalse();

    @Modifying
    @Query("UPDATE VideoContent v SET v.likes = v.likes + 1 WHERE v.id = :vid")
    void increaseLikeCount(@Param("vid")Long vid);

    @Modifying
    @Query("UPDATE VideoContent v SET v.likes = v.likes - 1 WHERE v.id = :vid")
    void decreaseLikeCount(@Param("vid")Long vid);

    @Modifying
    @Query("UPDATE VideoContent v SET v.views = v.views - 1 WHERE v.id = :vid")
    void  increaseViewCount(@Param(("vid")) Long vid);

    @Modifying
    @Query("UPDATE VideoContent v SET v.blocked = CASE WHEN v.blocked = true THEN false ELSE true END WHERE v.id = :vId")
    void  lockVideo(@Param("vId") Long vId);

}
