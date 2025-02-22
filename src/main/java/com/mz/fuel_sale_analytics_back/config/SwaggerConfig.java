package com.mz.fuel_sale_analytics_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mz.fuel_sale_analytics_back"))
                .paths(regex("/.*"))
                .build()
                .apiInfo(getInfo());
    }

    private ApiInfo getInfo() {
        return new ApiInfo(
                "Preço de Combustíveis",
                "API desenvolvida com Spring Boot para a seleção da Indra",
                "1.0",
                "",
                new Contact("Claudiomar Araújo",
                        "https://gitlab.com/claudiomarpda/selecao-java",
                        "claudiomar.development@gmail.com"),
                "",
                ""
        );
    }
}
