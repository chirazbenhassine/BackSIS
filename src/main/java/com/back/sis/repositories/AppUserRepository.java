package com.back.sis.repositories;

import com.back.sis.entities.AppUser;
import com.back.sis.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    /**
     * method return ( User : Authentification)
     *
     * @param username
     * @return user (AppUser)
     */
    AppUser findByUsername(String username);
}
