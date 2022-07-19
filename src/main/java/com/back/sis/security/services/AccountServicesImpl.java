package com.back.sis.security.services;

import com.back.sis.entities.AppRole;
import com.back.sis.entities.AppUser;
import com.back.sis.repositories.AppRoleRepository;
import com.back.sis.repositories.AppUserRepository;
import com.back.sis.security.services.AccountServices;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Class of service Layer (Couche Service)
 */
@Service
/**
 * Generate of transactions
 */

@Transactional
public class AccountServicesImpl implements AccountServices {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
   // private PasswordEncoder passwordEncoder;

    public AccountServicesImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepositoryder) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        //pour encoder pw à chaque création de user
       // String pw=appUser.getPassword();
        //appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);

    }

    @Override
    public AppUser loadUserByUserName(String userName) {
        return appUserRepository.findByUsername(userName);
    }

    @Override
    public List<AppUser> listUsers() {

        return appUserRepository.findAll();
    }
}
