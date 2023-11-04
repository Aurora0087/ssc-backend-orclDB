package com.example.app1.post.videoPost.viewerResponse.likes;

import com.example.app1.post.videoPost.VideoContent;
import com.example.app1.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    public String uploadLikeDetails(AppUser user, VideoContent content){

        Likes likes= new Likes(user,content);
        likeRepo.save(likes);
        return "done.";
    }

    @Transactional
    public String deleteLikeDetails(String uun,Long vid){

        likeRepo.deleteExistingLikeUser(uun,vid);
        return "done.";
    }
}
