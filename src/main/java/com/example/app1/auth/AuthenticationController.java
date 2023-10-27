package com.example.app1.auth;

import com.example.app1.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/auth")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request){

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        if (authenticate.isAuthenticated()){

            String generatedToken = jwtService.generateToken(request.getUsername());

            //For jwt cookie
            ResponseCookie JwToken = ResponseCookie.from("token",generatedToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(TimeUnit.DAYS.toSeconds(1))
                    .build();
            //for username
            ResponseCookie User = ResponseCookie.from("uun", request.getUsername())
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(TimeUnit.DAYS.toSeconds(1))
                    .build();


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE,JwToken.toString());
            headers.add(HttpHeaders.SET_COOKIE,User.toString());

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body
                    (new AuthenticationResponse("success",generatedToken));
        }
        else {
            throw new UsernameNotFoundException("User not found.");
        }
    }
}
