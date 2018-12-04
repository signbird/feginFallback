package org.bqf.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@EnableFeignClients(basePackages = {"org.bqf"})
@SpringBootApplication
@ComponentScan(basePackages = {"org.bqf"})
public class ServiceApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}
