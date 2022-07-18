package com.back.sis.security;

import com.back.sis.entities.AppRole;
import com.back.sis.entities.AppUser;
import com.back.sis.repositories.AppRoleRepository;
import com.back.sis.repositories.AppUserRepository;
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
    public AccountServicesImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
    }

    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    @Override
    public AppUser addNewUser(AppUser appUser) {
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
