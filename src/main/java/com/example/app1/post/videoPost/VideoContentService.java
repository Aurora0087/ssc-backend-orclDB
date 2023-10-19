package com.example.app1.post.videoPost;

import com.example.app1.user.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoContentService {

    @Autowired
    private VideoContentRepo contentRepo;

    public String uploadVideo(MultipartFile file) throws IOException {

        String videoService="src/main/resources/video";

        if (file.isEmpty()){
            return "Please select a file.";
        }

        String originalFileName = file.getOriginalFilename();

        String newName = UUID.randomUUID() + originalFileName;

        Path uploadPath = Paths.get(videoService+File.separator+newName);

        File f=new File(videoService);
        if (!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(),uploadPath);

        return newName;
    }

    public ResponseEntity<String> postVideoContent(VideoContent content){

        contentRepo.save(content);
        return ResponseEntity.status(HttpStatus.OK).body("Video Posted");
    }

    public ResponseEntity<List<VideoContentResponse>> getVideoContents(){

        List<VideoContent> videoContents = contentRepo.findAll();
        List<VideoContentResponse> contentResponses = new ArrayList<>();

        for (VideoContent videoContent:videoContents){
            VideoContentResponse response = new VideoContentResponse();

            response.setTitle(videoContent.getTitle());
            response.setDescription(videoContent.getDescription());
            response.setSkill(videoContent.getSkill());
            response.setVideoUrl(videoContent.getVideoUrl());
            response.setPostedTime(videoContent.getPostDate());

            UserProfile profile=new UserProfile();
            profile.setFirstName(videoContent.getUser().getFirstName());
            profile.setLastName(videoContent.getUser().getLastName());
            profile.setProfileImage(videoContent.getUser().getProfileImage());
            response.setProfile(profile);

            contentResponses.add(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(contentResponses);
    }

}
