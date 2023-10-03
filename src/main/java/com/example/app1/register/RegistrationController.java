package com.example.app1.register;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private final RegistrationService registrationService;

    @GetMapping(path = "/admin")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Its for admin.");
    }

    @GetMapping(path = "/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("Its for user.");
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request){

        String message = registrationService.register(request);

        return new ResponseEntity<>(new RegistrationResponse(message), HttpStatus.OK);
    }
}
