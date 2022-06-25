package com.evanaurelrius.seacademy;

import com.evanaurelrius.seacademy.controller.ProductController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SeaTask2Application {

	public static void main(String[] args) {
		new File(ProductController.uploadDir).mkdir();
		SpringApplication.run(SeaTask2Application.class, args);
	}

}
