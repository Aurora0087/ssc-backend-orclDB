package com.example.app1.admin;

import com.example.app1.post.videoPost.VideoContent;
import com.example.app1.post.videoPost.VideoContentRepo;
import com.example.app1.user.AppUser;
import com.example.app1.user.AppUserRepo;
import com.example.app1.user.UserProfile;
import com.example.app1.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private AppUserRepo userRepo;

    @Autowired
    private VideoContentRepo videoContentRepo;

    //view all profile[not password]
    public ResponseEntity<List<AdminViewProfilesResponse>> viewProfilesResponseEntity(){

        List<AppUser> users = userRepo.findAll();
        List<AdminViewProfilesResponse> responseList = new ArrayList<>();

        for (AppUser user:users){
            if (user.getUserRole()!= UserRole.ADMIN) {
                AdminViewProfilesResponse response = new AdminViewProfilesResponse();

                response.setFirstName(user.getFirstName());
                response.setLastName(user.getLastName());
                response.setEmail(user.getEmail());
                response.setProfileImage(user.getProfileImage());
                response.setPro(user.getPro());
                response.setLock(user.getLocked());
                response.setRole(String.valueOf(user.getUserRole()));

                responseList.add(response);
            }
        }

        return ResponseEntity.ok(responseList);
    }

    //search by name

    //block / unBlock user
    @Transactional
    public ResponseEntity<String> lockUser(String uEmail){

        if (!Objects.equals(uEmail, "admin@ssc.com")){
            userRepo.lockUser(uEmail);
        }

        return null;
    }

    //view all videos
    public ResponseEntity<List<AdminVideoDetailsResponse>> getAllTypeVideoDetails(){

        List<VideoContent> contents= videoContentRepo.findAll();
        List<AdminVideoDetailsResponse> videoDetailsResponse = new ArrayList<>();
        for (VideoContent content:contents){
            AdminVideoDetailsResponse response = new AdminVideoDetailsResponse();

            response.setVId(content.getId());
            response.setTitle(content.getTitle());
            response.setDescription(content.getDescription());
            response.setSkill(content.getSkill());
            response.setLikes(content.getLikes());
            response.setDislike(content.getDislikes());
            response.setViews(content.getViews());
            response.setPostedTime(content.getPostDate());
            response.setVideoUrl(content.getVideoUrl());
            response.setIsFree(content.getFree());

            UserProfile profile = new UserProfile(content.getUser().getFirstName(),content.getUser().getLastName(),content.getUser().getProfileImage());
            response.setProfile(profile);
            response.setIsLocked(content.getBlocked());
        }
        return ResponseEntity.ok(videoDetailsResponse);
    }

    //search videos by userName

    //block / unBlock videos
    public ResponseEntity<String> changeLockInVideo(Long vId){

        videoContentRepo.lockVideo(vId);
        return ResponseEntity.ok("done");
    }

    //view feedBacks

}
