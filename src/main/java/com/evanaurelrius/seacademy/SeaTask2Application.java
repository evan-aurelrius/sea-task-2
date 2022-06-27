package com.evanaurelrius.seacademy;

import com.evanaurelrius.seacademy.controller.ProductController;
import com.evanaurelrius.seacademy.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class SeaTask2Application {

	@Autowired
	BalanceService balanceService;

	public static void main(String[] args) {
		new File(ProductController.uploadDir).mkdir();
		SpringApplication.run(SeaTask2Application.class, args);
	}

	@Bean
	public CommandLineRunner setDefaultBalanceIfUnset() {
		return args -> {
			balanceService.setDefaultBalanceIfUnset();
		};
	}

}
