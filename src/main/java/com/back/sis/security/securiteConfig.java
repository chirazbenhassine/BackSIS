package com.back.sis.security;


import com.back.sis.entities.AppUser;
import com.back.sis.security.jwt.filtres.jwtAuthentificationFilter;
import com.back.sis.security.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class securiteConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccountServices accountServices;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    /* Quand user s'authentifie :
    * utilise moi cette method userDetailService() pour récupérer user avec role à partir couche service
    * userDetailsServices: method etant un objet(new), une classe qui implements interface c'est userDetailsServices
    */
       /* auth.userDetailsService(new UserDetailsService() {
            //cad quanf user saisie le username et pw il fait appel a cette methode
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser appUser=accountServices.loadUserByUserName(username);
                // Convertir AppRoles type de collection de roles  to Collection GrandAuthority
                Collection<GrantedAuthority> authorities=new ArrayList<>();
                appUser.getAppRoles().forEach(role->{
                    authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
                });
                //User() classe objet user de spring user ( string,String, collection de type <GrantedAuthority> )
                return new User(appUser.getUsername(), appUser.getPassword(), authorities);
            }*/

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Tout les requetes au démarrage nécessite pas une Authentification */

        /* pour auth statefull, avec la session :
         * il faut laisser (mettre) http.csrf.disable car elle protége contre les attaques de type csrf
         */
        //http.csrf().disable();
        /* Si on désactive cette ligne en dessus, on remarque que le formulaire affiche: le serveur lui donne
        mais si on fait inspecter: on remarque que il y a champ name ="csrf token" de type ="hidden" ,
        car chaque fois on demande une page, il génére un token
        et a chaque authentification il compare le token s'il est la meme ou non (voir explication de stateless auth)
         */
        //http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Utilisation de auth Stateless : gérer les sessions en utilisant JWT tOKEN
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/phpmyadmin/*");
        //http.authorizeRequests().anyRequest().permitAll();
        /* pour accéder à l'authentifiaction il faut passer par http.formlogin()
        en SpringSecurity quand user va accéder à une ressources
        qui n'a pas le droit, il lui affiche un formulaire d'authentification
        ==> dans ce cas un formulaire est formé par le SpringSecurity par defaut
        */
        //http.formLogin(); /* on va désactiver car ne fonctionne plus en auth stateless, on va développer coté front*/
        http.authorizeRequests().anyRequest().authenticated(); //authenticated cad chaque systéme va passer par authenfication
        //Pour indiquer que il y a un filtre installé
       http.addFilter(new jwtAuthentificationFilter(authenticationManagerBean()));
    }

   @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}
