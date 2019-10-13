package com.sippulse.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class PetSchedule {
	public static void main(String[] args) {
		SpringApplication.run(PetSchedule.class, args);
	}

	@Bean

	public Docket newsApi() {

		return new Docket(DocumentationType.SWAGGER_2).groupName("cruds").apiInfo(apiInfo()).select()
				.paths(regex("/clientes.*|/funcionarios.*|/pets.*|/consultas.*")).build();
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("Aplicação CRUD - PetSchedule")
				.description("Aplicação desenvolvida com Spring Boot para processo seletivo da SIPPulse.")
				.contact("Eduardo José Schmitt").build();
	}
}
