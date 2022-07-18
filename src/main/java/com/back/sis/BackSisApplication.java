package com.back.sis;

import com.back.sis.entities.AppRole;
import com.back.sis.entities.AppUser;
import com.back.sis.security.AccountServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class BackSisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackSisApplication.class, args);
	}

}