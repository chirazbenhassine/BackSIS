package com.back.sis.security.jwt.filtres;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class jwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    //pour faire le filtre : besoin d'objet fournit par spring security

    private AuthenticationManager authenticationManager;

    /**
     * Constructor
     * @param authenticationManager
     */
    public jwtAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    //Quand user est s'authentifié

    /**
     *
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OpenID).
     * @return authentication
     * @throws AuthenticationException
     */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //Récupération des données
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println("username");
        System.out.println("password");
        //Objet UsernameAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(username,password);
        //authenticate() : method fait l'appel à UserService, l'appel à la methode qui va recupérer de la bd
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     * method.
     * @throws IOException
     * @throws ServletException
     */
    //Quand user a reussi l'authentification
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       System.out.println("succesfulAuthentication");
       //Cast (User) car la method getPrincipal() return un objet de type object, c'est pas générique, donc il faut faire le cast
       User user=(User) authResult.getPrincipal(); //return user qui est s'authentifie
        //algorithm pour la signature : j'ai utilisé HMAC256, il demande clé privé : symétrique
        Algorithm algorithm=Algorithm.HMAC256("mySecret1234");
        //Pour générer le Token (jwt)
        String jwtAccessToken= JWT.create()
                .withSubject(user.getUsername()) //subject contient username (payload)
                .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000)) //date d'expiration exp en ms: on veut 5 minutes donc *60 (s) *1000 (piur donner en ms)
                .withIssuer(request.getRequestURL().toString())// le nom d'application qui va générer le token: URL de token
                /* Java 8
                Pour le role de user
                on a un pb de getAuthorities car withClaim accepte que liste de double ou String et getAuthorities c'est de type authorities
                solution: convertir en liste de string:
                soit la méthod classique
                soit en java 8:
                appel method stream, aprés map pour convertir object vers un autre
                aprés appel de la methode collect qui va convertir ga-> etc en une liste
                 */
                .withClaim("roles",user.getAuthorities().stream().map(ga ->ga.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm); // Calcule de la signature avec algorithme HMAC256
         //Envoie jwt dans header vers le client
        response.setHeader("authorization",jwtAccessToken);

    }
}
