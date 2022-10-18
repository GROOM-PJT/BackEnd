package com.goorm.baromukja.baseUtil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/04
 */

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.goorm.baromukja"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(apiKey());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        List<SecurityReference> securityReference = new ArrayList<>();
        securityReference.add(new SecurityReference("AccessToken", authorizationScopes));
        securityReference.add(new SecurityReference("RefreshToken", authorizationScopes));
        return securityReference;
    }

    private List<SecurityScheme> apiKey() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("AccessToken", "Authorization", "header"));
        apiKeyList.add(new ApiKey("RefreshToken", "Authorization-refresh", "header"));
        return apiKeyList;
    }


    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title("Groom API Documentation")
                .description("Groom Project Team 4 Server API Document.")
                .license("baro mukja")
                .licenseUrl("br-ao muk-ja")
                .version("1")
                .build();
    }

}