package com.example.app1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitializer implements CommandLineRunner {

    @Autowired
    private AppUserService userService;

    @Override
    public void run(String... args) throws Exception {
        AppUser user= new AppUser("main",
                "admin",
                "admin@ssc.com",
                "1234",
                UserRole.ADMIN);
        userService.signUpUser(user);
    }
}
