package com.cartservice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
//@EnableEurekaClient
@EnableFeignClients

public class CartserviceApplication {

	//This method acts as main method from where the execution of the service begins.
	public static void main(String[] args) {
		SpringApplication.run(CartserviceApplication.class, args);
		System.out.println("CartService running successfullu..");
	}
}
