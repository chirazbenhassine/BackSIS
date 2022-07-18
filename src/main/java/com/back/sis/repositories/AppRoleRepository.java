package com.back.sis.repositories;

import com.back.sis.entities.AppRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    /**
     * method return role
     *
     * @param roleName
     * @return role (AppRole)
     */
    AppRole findByRoleName(String roleName);
}