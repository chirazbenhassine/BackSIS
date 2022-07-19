package com.back.sis.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    //Pour assurer que le mdp invisible en JSON
    // WRITE_ONLY cad juste prend le mdp mais n'est pas accessible aux getters et sertters
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
