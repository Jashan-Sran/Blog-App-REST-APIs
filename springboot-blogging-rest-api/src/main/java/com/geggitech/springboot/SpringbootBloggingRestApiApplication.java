package com.geggitech.springboot;

import com.geggitech.springboot.entity.Role;
import com.geggitech.springboot.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
					title = "Spring Boot Application Documentation",
				description = "Blog App REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Jashandeep Singh Sran",
						email = "jashan.sran707@gmail.com",
						url = "https://sranTech.com"
						),
				license = @License(
						name = "Tech 1.0",
						url = "https://sranTech.com/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App REST APIs documentation ",
				url = "https://github.com"
		)
)
public class SpringbootBloggingRestApiApplication implements CommandLineRunner {

	   @Autowired
       private RoleRepository repository;


	public static void main(String[] args) {

		SpringApplication.run(SpringbootBloggingRestApiApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		Role adminUser = new Role();

		adminUser.setName("ROLE_ADMIN");

		repository.save(adminUser);

		Role normalUser = new Role();

		normalUser.setName("ROLE_USER");

		repository.save(normalUser);

	}
}
