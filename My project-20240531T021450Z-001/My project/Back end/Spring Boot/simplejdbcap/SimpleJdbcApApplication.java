package com.example.simplejdbcap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
//@ServletComponentScan
@SpringBootApplication
public class SimpleJdbcApApplication {
	
	public static void main(String args[]) {
		SpringApplication.run(SimpleJdbcApApplication.class,args);
	}

}
