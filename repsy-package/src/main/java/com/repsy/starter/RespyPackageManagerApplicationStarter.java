package com.repsy.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.respy"})
@ComponentScan(basePackages = {"com.respy"})
@EnableJpaRepositories(basePackages = {"com.respy"})
@SpringBootApplication(scanBasePackages = "com.respy")
public class RespyPackageManagerApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(RespyPackageManagerApplicationStarter.class, args);
	}

}
