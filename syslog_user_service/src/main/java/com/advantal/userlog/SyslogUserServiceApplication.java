package com.advantal.userlog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class SyslogUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyslogUserServiceApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMappper() {
		return new ModelMapper();
	}
	
}
