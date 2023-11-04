package com.example.app1.post.videoPost;


import com.example.app1.post.videoPost.viewerResponse.likes.LikeRepo;
import com.example.app1.post.videoPost.viewerResponse.likes.LikeService;
import com.example.app1.user.AppUser;
import com.example.app1.user.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(path = "/video")
@AllArgsConstructor
@CrossOrigin
public class VideoContentController {

    @Autowired
    private AppUserRepo userRepo;
    @Autowired
    private VideoContentRepo contentRepo;
    @Autowired
    private VideoContentService contentService;
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private LikeService likeService;


    @PostMapping(path = "/doLike/{vid}")
    public ResponseEntity<String> doLike(
            @CookieValue(name = "uun") String userName,
            @PathVariable Integer vid
    ){

        boolean e=likeRepo.existLikedUser(userName,Long.valueOf(vid));
        //System.out.println(e);
        if (!e){
            contentService.increaseLikeCount(Long.valueOf(vid));
            AppUser user = extractUser(userName);
            VideoContent videoContent = contentRepo.findById(Long.valueOf(vid)).orElseThrow();
            likeService.uploadLikeDetails(user,videoContent);
        }
        else {
            contentService.decreaseLikeCount(Long.valueOf(vid));
            likeService.deleteLikeDetails(userName,Long.valueOf(vid));
        }

        return ResponseEntity.ok("done");
    }

    private AppUser extractUser(String userName){
        if (userName==null)return null;
        return userRepo.findByEmail(userName).orElseThrow();
    }
}
