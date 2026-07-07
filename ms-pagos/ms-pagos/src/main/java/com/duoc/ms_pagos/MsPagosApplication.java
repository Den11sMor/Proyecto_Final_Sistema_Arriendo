package com.duoc.ms_pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Clase principal del microservicio de pagos.
 */
@EnableFeignClients
@SpringBootApplication
public class MsPagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPagosApplication.class, args);
	}
}