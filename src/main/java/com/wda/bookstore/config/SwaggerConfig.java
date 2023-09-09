package com.wda.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.wda.bookstore";
    private static final String API_TITLE = "Bookstore Manager";
    private static final String API_DESCRIPTION = "Bookstore Manager API";
    private static final String API_VERSION = "1.0.0";
    private static final String CONTACT_NAME = "Pedro Lucas";
    private static final String CONTACT_GITHUB = "https://github.com/PedroLucas3542";
    private static final String CONTACT_EMAIL = "portelacarlosp@gmail.com";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(
                        new Tag("Users", "Management of Users"),
                        new Tag("Rentals", "Management of Rentals"),
                        new Tag("Publishers", "Management of Publishers"),
                        new Tag("Books", "Management of Users")
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
                .build();
    }
}
