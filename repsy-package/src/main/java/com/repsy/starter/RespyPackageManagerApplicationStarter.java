package com.repsy.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.repsy"})
@ComponentScan(basePackages = {"com.repsy"})
@EnableJpaRepositories(basePackages = {"com.repsy"})
@SpringBootApplication(scanBasePackages = "com.repsy")
public class RespyPackageManagerApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(RespyPackageManagerApplicationStarter.class, args);
	}

}
