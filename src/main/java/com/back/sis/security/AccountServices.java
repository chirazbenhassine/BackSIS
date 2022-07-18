package com.back.sis.security;

import com.back.sis.entities.AppRole;
import com.back.sis.entities.AppUser;
import java.util.List;

public interface AccountServices {
    /**
     * method add User
     * @param appUser
     * @return user
     */
    AppUser addNewUser(AppUser appUser);
    /**
     * method add Role
     * @param appRole
     * @return role
     */
    AppRole addNewRole(AppRole appRole);

    /**
     * method add role to user
     * @param username
     * @param roleName
     */
    void addRoleToUser(String username,String roleName);

    /**
     * method return User : done username return User
     * @param userName
     * @return user
     */
    AppUser loadUserByUserName (String userName);

    /**
     * method return Users "List" API REST
     * @return ListUsers
     */
    List<AppUser> listUsers();
}
