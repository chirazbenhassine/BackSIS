package com.back.sis.web;

import com.back.sis.entities.AppRole;
import com.back.sis.entities.AppUser;
import com.back.sis.security.services.AccountServices;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountRestController {

    /* Pour faire la list des utilisateurs, soit autowired soit attribute + constructor*/
    private AccountServices accountServices; //c'est pour afficher la liste fes utilisateurs

    public AccountRestController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }
    //@GetMapping Pour accéder a cette methode
    @GetMapping(path = "/users")
    public List<AppUser> appUsers(){
        return accountServices.listUsers();
    }

    //method add user
    @PostMapping(path = "/users")
    //@RequestBody cad les donnes se trouve dans la requete
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountServices.addNewUser(appUser); // Important car ici il va encoder le mdp
    }

    //method add role
    @PostMapping(path = "/roles")
    //@RequestBody cad les donnes se trouve dans la requete
    public AppRole saveRole(@RequestBody AppRole appRole){
        return accountServices.addNewRole(appRole);
    }

    //method add roleToUser
    @PostMapping(path = "/addRoleToUser")
    //@RequestBody cad les donnes se trouve dans la requete
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
        accountServices.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRolename());
    }

}

/* Il ne faut pas créer class Intern :
Soit faire class ici dans ce fichier
Soit créer class dans le package
 */

@Data //pour les getters et setters (get/set)
class RoleUserForm{
    private String username;
    private String rolename;
}
