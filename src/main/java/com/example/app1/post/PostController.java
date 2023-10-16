package com.example.app1.post;

import com.example.app1.jwt.JwtService;
import com.example.app1.post.videoPost.VideoContent;
import com.example.app1.post.videoPost.VideoContentResponse;
import com.example.app1.post.videoPost.VideoContentService;
import com.example.app1.post.videoPost.VideoUploadRequest;
import com.example.app1.user.AppUser;
import com.example.app1.user.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppUserRepo userRepo;
    @Autowired
    private FindTeacherService findTeacherService;
    @Autowired
    private VideoContentService videoContentService;

    @GetMapping(path = "/requestlist")
    public ResponseEntity<List<FindTeacherResponse>> viewAllFindTeacherRequest(){

        return findTeacherService.requestFindTeacher();
    }

    @PostMapping(path = "/postrequest")
    public ResponseEntity<String> postSkillWantedToLearner(
            @RequestBody FindTeacherRequest request,
            @RequestHeader("Authorization") String jwtToken
    ){

        AppUser user = extractUser(jwtToken);

        return findTeacherService.sendPost(new FindTeacher(
                request.getHeading(),
                request.getSkill(),
                request.getDescription(),
                request.getLocation(),
                new Date(),
                user
        ));
    }


    // Video controllers
    @PostMapping(path = "/videoUpload")
    public ResponseEntity<String> postVideo(
            @RequestHeader("Authorization") String jwtToken,
            @RequestParam("video") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("skill") String skill
    ) throws IOException {

        AppUser user = extractUser(jwtToken);
        String videoPath = videoContentService.uploadVideo(file);

        return videoContentService.postVideoContent(new VideoContent(
                title,
                description,
                skill,
                videoPath,
                user
        ));
    }

    @GetMapping(path = "/getVideos")
    public ResponseEntity<List<VideoContentResponse>> getVideos(){
        return videoContentService.getVideoContents();
    }



    private AppUser extractUser(String JWToken){
        String userName =null;
        String token =null;

        if (JWToken!=null && JWToken.startsWith("Bearer ")){
            token =JWToken.substring(7);
            userName = jwtService.extractUsername(token);
        }

        return userRepo.findByEmail(userName).orElseThrow();
    }
}
