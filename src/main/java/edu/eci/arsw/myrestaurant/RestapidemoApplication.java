package edu.eci.arsw.myrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import edu.eci.arsw.myrestaurant.services.OrderServicesException;

@SpringBootApplication
@ComponentScan(basePackages = "edu.eci.arsw.myrestaurant")
public class RestapidemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapidemoApplication.class, args);
		
	}
}
