package com.backend.videocall;

import com.backend.videocall.user.User;
import com.backend.videocall.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VideocallApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideocallApplication.class, args);

	}
	@Bean
	public CommandLineRunner commandLineRunner(
			UserService service
	){
		return args -> {
//			 ✅ Using constructor instead of builder
			service.register(new User("atul", "atul@gmail.com", "123","offline"));
			service.register(new User("mrinal", "mrinal@gmail.com", "456","offline"));
			service.register(new User("prashant", "prashant@gmail.com", "789","offline"));
		};

	}

}
