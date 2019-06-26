package com.ticket.scan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ticket.scan.dao.mapper")
public class ScanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScanApplication.class, args);
	}

}
