package com.example.app1.admin;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/getUserDetails")
    public ResponseEntity<List<AdminViewProfilesResponse>> getUserDetails(){
        return adminService.viewProfilesResponseEntity();
    }

    @PostMapping(path = "/lockUser/{uEmail}")
    public ResponseEntity<String> changeUserLock(
            @PathVariable String uEmail
    ){
        return adminService.lockUser(uEmail);
    }

    public ResponseEntity<List<AdminVideoDetailsResponse>> getVideoDetails(){
        return adminService.getAllTypeVideoDetails();
    }
}
