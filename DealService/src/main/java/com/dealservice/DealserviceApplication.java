package com.dealservice;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


@EnableFeignClients

//Enable Eureka Client for service registration with Eureka server

//Spring Boot application annotation
@SpringBootApplication
public class DealserviceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(DealserviceApplication.class, args);
		System.out.println("deal running successfully");
	}
	
	
}
