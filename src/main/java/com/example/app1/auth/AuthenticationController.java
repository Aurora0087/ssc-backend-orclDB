package com.example.app1.auth;

import com.example.app1.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/auth")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        if (authenticate.isAuthenticated()){

            String generatedToken = jwtService.generateToken(request.getUsername());

            return new ResponseEntity<>(new AuthenticationResponse("success",generatedToken), HttpStatus.OK);
        }
        else {
            throw new UsernameNotFoundException("User not found.");
        }
    }
}
