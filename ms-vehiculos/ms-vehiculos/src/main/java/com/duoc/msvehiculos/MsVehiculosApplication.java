package com.duoc.msvehiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsVehiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVehiculosApplication.class, args);
	}

}

