package com.example.app1.register;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private final RegistrationService registrationService;


    @GetMapping(path = "/api/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Its for admin.");
    }

    @GetMapping(path = "/api/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("Its for user.");
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request){

        return registrationService.register(request);
    }
}
