package com.example.app1.updateProfile;

import com.example.app1.user.AppUserService;
import com.example.app1.user.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
public class ProfileController {

    @Autowired
    private AppUserService userService;

    @PostMapping(path = "/api/user/email")
    public ResponseEntity<String> changeUserEmail(@RequestBody EmailChangeRequest request){
        return userService.changeEmail(request.getOldEmail(),request.getNewEmail());
    }
    @GetMapping(path = "/profile")
    public ResponseEntity<UserProfile> getProfile(
            @CookieValue(name = "uun") String uun
    ){
        return userService.profile(uun);
    }
}
