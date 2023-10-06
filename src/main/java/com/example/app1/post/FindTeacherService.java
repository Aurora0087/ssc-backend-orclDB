package com.example.app1.post;

import com.example.app1.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
