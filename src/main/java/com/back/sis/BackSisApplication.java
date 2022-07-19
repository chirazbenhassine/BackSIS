package com.back.sis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackSisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackSisApplication.class, args);
	}

@Bean
 PasswordEncoder passwordEncoder(){
	return new BCryptPasswordEncoder();
}


/** @Bean
	CommandLineRunner start(AccountServices accountServices){
		return args -> {
			accountServices.addNewRole(new AppRole(null,"ADMIN"));
			accountServices.addNewRole(new AppRole(null,"AGENT"));

			accountServices.addNewUser(new AppUser(null,"agent2","12345",new ArrayList<>()));
			accountServices.addNewUser(new AppUser(null,"agent1","123455",new ArrayList<>()));
			accountServices.addNewUser(new AppUser(null,"admin","12345",new ArrayList<>()));

            accountServices.addRoleToUser("agent2","AGENT");
			accountServices.addRoleToUser("agent1","AGENT");
			accountServices.addRoleToUser("admin","ADMIN");

		};
	}**/
}