package com.taskManagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableCaching
public class TaskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
		
		BCryptPasswordEncoder b=new BCryptPasswordEncoder();
		String e=b.encode("abcd");
		System.out.println(e);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
