package com.example.app1.post;

import com.example.app1.jwt.JwtService;
import com.example.app1.user.AppUser;
import com.example.app1.user.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppUserRepo userRepo;
    @Autowired
    private FindTeacherService findTeacherService;

    @GetMapping(path = "/api/requestlist")
    public ResponseEntity<List<FindTeacherResponse>> viewAllFindTeacherRequest(){

        return findTeacherService.requestFindTeacher();
    }

    @PostMapping(path = "/api/postrequest")
    public ResponseEntity<String> postSkillWantedToLearner(
            @RequestBody FindTeacherRequest findTeacherRequest,
            @RequestHeader("Authorization") String jwtToken
    ){

        String userName =null;
        String token=null;

        if (jwtToken!=null && jwtToken.startsWith("Bearer ")){
            token=jwtToken.substring(7);
            userName=jwtService.extractUsername(token);
        }

        AppUser user = userRepo.findByEmail(userName).orElseThrow();

        return findTeacherService.sendPost(new FindTeacher(
                findTeacherRequest.getHeading(),
                findTeacherRequest.getSkill(),
                findTeacherRequest.getDescription(),
                findTeacherRequest.getLocation(),
                new Date(),
                user
        ));
    }
}
