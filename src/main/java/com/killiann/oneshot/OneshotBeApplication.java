package com.killiann.oneshot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class OneshotBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneshotBeApplication.class, args);
	}

}
