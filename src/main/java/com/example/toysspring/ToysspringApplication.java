package com.example.toysspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ToysspringApplication {

	public static void main(String[] args) {
		// SpringApplication.run(ToysspringApplication.class, args);

		SpringApplication app = new SpringApplication(ToysspringApplication.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.run(args);
	}

}
