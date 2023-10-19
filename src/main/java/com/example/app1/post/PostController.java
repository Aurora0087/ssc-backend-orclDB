package com.example.app1.post;

import com.example.app1.jwt.JwtService;
import com.example.app1.post.videoPost.VideoContent;
import com.example.app1.post.videoPost.VideoContentResponse;
import com.example.app1.post.videoPost.VideoContentService;
import com.example.app1.stream.StreamService;
import com.example.app1.user.AppUser;
import com.example.app1.user.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
    @Autowired
    private StreamService streamService;

    @GetMapping(path = "/requestlist")
    public ResponseEntity<List<FindTeacherResponse>> viewAllFindTeacherRequest(){

        return findTeacherService.requestFindTeacher();
    }

    @PostMapping(path = "/postrequest")
    public ResponseEntity<String> postSkillWantedToLearner(
            @RequestBody FindTeacherRequest request,
            @CookieValue(name = "uun") String userName
    ){
        System.out.println(userName);

        AppUser user = extractUser(userName);

        return findTeacherService.sendPost(new FindTeacher(
                request.getHeading(),
                request.getSkill(),
                request.getDescription(),
                request.getLocation(),
                new Date(),
                user
        ));
        //return ResponseEntity.ok("5478");
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


    @GetMapping(path = "/video/{videoName}", produces = "video/mp4")
    public ResponseEntity<Resource> videoS(@PathVariable String videoName){
        return streamService.getVideo(videoName);
    }

    private AppUser extractUser(String userName){
        if (userName==null)return null;
        return userRepo.findByEmail(userName).orElseThrow();
    }
}
