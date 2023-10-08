package com.example.app1.post;

import com.example.app1.user.AppUserService;
import com.example.app1.user.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindTeacherService {
    @Autowired
    private FindTeacherRepo findTeacherRepo;
    @Autowired
    private AppUserService userService;

    public ResponseEntity<String> sendPost(FindTeacher findTeacher){

        if (!userService.isUserExist(findTeacher.getAppUser())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist.");
        }

        findTeacherRepo.save(findTeacher);

        return ResponseEntity.status(HttpStatus.OK).body("Posted.");
    }

    public ResponseEntity<List<FindTeacherResponse>> requestFindTeacher(){

        List<FindTeacher> findTeachers = findTeacherRepo.findAll();
        List<FindTeacherResponse> findTeacherResponses = new ArrayList<>();

        for (FindTeacher findTeacher:findTeachers){
            FindTeacherResponse response =new FindTeacherResponse();

            response.setHeader(findTeacher.getHeading());
            response.setDescription(findTeacher.getDescription());
            response.setSkill(findTeacher.getSkill());
            response.setLocation(findTeacher.getLocation());
            response.setPostedTime(findTeacher.getPostedTime());

            UserProfile profile=new UserProfile();
            profile.setFirstName(findTeacher.getAppUser().getFirstName());
            profile.setLastName(findTeacher.getAppUser().getLastName());
            profile.setProfileImage("https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
            response.setProfile(profile);

            findTeacherResponses.add(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(findTeacherResponses);
    }
}
