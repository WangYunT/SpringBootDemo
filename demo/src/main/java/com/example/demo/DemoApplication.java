package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.HttpsClientRequestFactory;

@SpringBootApplication
public class DemoApplication {
	
	@Bean
	public RestTemplate httpsRestTemplate() {
		RestTemplate restClient =  new RestTemplate (new HttpsClientRequestFactory());
		return restClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

