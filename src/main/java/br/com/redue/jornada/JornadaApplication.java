package br.com.redue.jornada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class JornadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JornadaApplication.class, args);
	}

}
