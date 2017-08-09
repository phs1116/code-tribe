package com.hwatu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by hwatu on 2017. 8. 8..
 */
@SpringBootApplication
public class DomainApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DomainApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DomainApplication.class, args);
	}
}
