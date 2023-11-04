package com.example.app1.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    //Query for finding 1 user by email
    @Query("SELECT u FROM AppUser u WHERE u.email = :email")
    Optional<AppUser> findByEmail(@Param("email") String email);

    //Query for changing email
    @Modifying
    @Query("UPDATE AppUser u SET u.email = :newEmail WHERE u.email = :oldEmail")
    void updateEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail);

    @Modifying
    @Query("UPDATE AppUser u SET u.locked = CASE WHEN u.locked = true THEN false ELSE true END WHERE u.email = :uEmail")
    void lockUser(@Param("uEmail") String uEmail);

}
