package com.example.app1.post.videoPost.viewerResponse.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepo extends JpaRepository<Likes,Long> {

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Likes l " +
            "WHERE l.user.email = :uun AND l.videoContent.id = :contentId")
    boolean existLikedUser(@Param("uun") String uun, @Param("contentId") Long contentId);

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.user.email = :uun AND l.videoContent.id = :contentId")
    void deleteExistingLikeUser(@Param("uun") String uun, @Param("contentId") Long contentId);
}
