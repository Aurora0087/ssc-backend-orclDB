package com.example.app1.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepo appUserRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User do not exist."));
    }


    //User registration
    public ResponseEntity<String> signUpUser(AppUser appUser){

        if (isUserExist(appUser)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepo.save(appUser);

        return ResponseEntity.status(HttpStatus.OK).body("User registration done.");
    }

    public Boolean isUserExist(AppUser appUser){
        return appUserRepo.findByEmail(appUser.getEmail()).isPresent();
    }

}
