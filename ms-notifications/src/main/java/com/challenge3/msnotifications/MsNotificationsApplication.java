package com.challenge3.msnotifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class MsNotificationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsNotificationsApplication.class, args);
	}

}
