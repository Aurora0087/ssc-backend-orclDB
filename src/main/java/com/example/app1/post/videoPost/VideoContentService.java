package com.example.app1.post.videoPost;

import com.example.app1.user.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoContentService {

    @Autowired
    private VideoContentRepo contentRepo;


    //uploading video on local storage
    public String uploadVideo(MultipartFile file) throws IOException {

        String videoService= new ClassPathResource("static/video/").getFile().getAbsolutePath();

        if (file.isEmpty()){
            return "Please select a file.";
        }

        String originalFileName = file.getOriginalFilename();

        String newName = UUID.randomUUID()+originalFileName;

        Path uploadPath = Paths.get(videoService+File.separator+newName);

        File f=new File(videoService);
        if (!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(),uploadPath);

        return newName;
    }

    public String uploadThumbnail(MultipartFile img) throws IOException {

        String imgService= new ClassPathResource("static/image/thumbnail").getFile().getAbsolutePath();

        if (img.isEmpty()){
            return "Please select a file";
        }

        String originalFileName  =img.getOriginalFilename();

        String newName = UUID.randomUUID()+originalFileName;
        Path uploadedPath = Paths.get(imgService+File.separator+newName);

        File f=new File(imgService);
        if (!f.exists()){
            f.mkdir();
        }

        Files.copy(img.getInputStream(),uploadedPath);

        return newName;
    }



    public ResponseEntity<String> postVideoContent(VideoContent content){

        contentRepo.save(content);
        return ResponseEntity.status(HttpStatus.OK).body("Video Posted");
    }

    public ResponseEntity<List<VideoContentResponse>> getVideoContents(){

        List<VideoContent> videoContents = contentRepo.findByBlockedFalse();
        List<VideoContentResponse> contentResponses = new ArrayList<>();

        for (VideoContent videoContent:videoContents){
            VideoContentResponse response = new VideoContentResponse();

            response.setTitle(videoContent.getTitle());
            response.setDescription(videoContent.getDescription());
            response.setSkill(videoContent.getSkill());
            response.setVideoId(videoContent.getId());
            response.setThumbnailUrl(videoContent.getThumbnailUrl());
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

    public ResponseEntity<VideoDetailsResponse> getVideoDetails(Long videoId){
        try {
            VideoContent content = contentRepo.findById(videoId).orElseThrow(()->new RuntimeException("error!!"));

            if (!content.getBlocked()) {
                VideoDetailsResponse response = new VideoDetailsResponse(
                        content.getTitle(),
                        content.getDescription(),
                        content.getSkill(),
                        content.getPostDate(),
                        content.getVideoUrl(),
                        content.getLikes(),
                        content.getDislikes(),
                        content.getViews(),
                        new UserProfile(
                                content.getUser().getFirstName(),
                                content.getUser().getLastName(),
                                content.getUser().getProfileImage()
                        )
                );

                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(new VideoDetailsResponse());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public void increaseLikeCount(Long vid){
        contentRepo.increaseLikeCount(vid);
    }
    @Transactional
    public void decreaseLikeCount(Long vid){
        contentRepo.decreaseLikeCount(vid);
    }
    @Transactional
    public void increaseViewCount(Long vid){
        contentRepo.increaseViewCount(vid);
    }

}
