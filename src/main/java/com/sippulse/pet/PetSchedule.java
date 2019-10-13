package com.sippulse.pet;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PetSchedule {
	public static void main(String[] args) {
		SpringApplication.run(PetSchedule.class, args);
	}

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(
					Arrays.asList(new ParameterBuilder()
			            .name("Authorization")
			            .description("JWT Token")
			            .modelRef(new ModelRef("string"))
			            .parameterType("header")
			            .required(false)
			            .build())
				)
				.groupName("cruds").apiInfo(apiInfo()).select()
				.paths(regex("/login.*|/clientes.*|/funcionarios.*|/pets.*|/consultas.*")).build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Aplicação CRUD - PetSchedule")
				.description("Aplicação desenvolvida com Spring Boot para processo seletivo da SIPPulse.")
				.contact("Eduardo José Schmitt").build();
	}
}
