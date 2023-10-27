package com.example.app1.post;

import com.example.app1.jwt.JwtService;
import com.example.app1.post.videoPost.VideoContent;
import com.example.app1.post.videoPost.VideoContentResponse;
import com.example.app1.post.videoPost.VideoContentService;
import com.example.app1.post.videoPost.VideoDetailsResponse;
import com.example.app1.stream.StreamService;
import com.example.app1.user.AppUser;
import com.example.app1.user.AppUserRepo;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@CrossOrigin
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
            @CookieValue(name = "uun") String userName,
            @RequestParam("video") MultipartFile video,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("skill") String skill
    ) throws IOException {

        AppUser user = extractUser(userName);
        String videoPath = "http://localhost:8080/api/video/" + videoContentService.uploadVideo(video);
        String thumbnailPath = "http://localhost:8080/api/thumbnail/" + videoContentService.uploadThumbnail(thumbnail);

        return videoContentService.postVideoContent(new VideoContent(
                title,
                description,
                skill,
                videoPath,
                thumbnailPath,
                user
        ));
    }

    @GetMapping(path = "/getVideos")
    public ResponseEntity<List<VideoContentResponse>> getVideos(){
        return videoContentService.getVideoContents();
    }

    @GetMapping(path = "/video-details/{vid}")
    public ResponseEntity<VideoDetailsResponse> getVideoDet(
            @PathVariable Integer vid
    ){
        return videoContentService.getVideoDetails(vid.longValue());
    }


    @GetMapping(path = "/video/{videoName}", produces = "video/mp4")
    public ResponseEntity<Resource> videoS(@PathVariable String videoName){
        return streamService.getFile(videoName,"video");
    }

    @GetMapping(path = "thumbnail/{thumbnailName}", produces = "image/*")
    public ResponseEntity<Resource> thumbnailS(@PathVariable String thumbnailName){
        return streamService.getFile(thumbnailName,"thumbnail");
    }

    private AppUser extractUser(String userName){
        if (userName==null)return null;
        return userRepo.findByEmail(userName).orElseThrow();
    }
}
