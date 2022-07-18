package com.back.sis.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    /**
     * when we load a user, role is also loaded automatically
     */
    @ManyToMany(fetch = FetchType.EAGER)
    /*
     List vide by default
     If we use "eager" List is not null
     ( don't let list = null comme : private Collection<AppRole> appRoles; )
    */
    private Collection<AppRole> appRoles =new ArrayList<>();

}
