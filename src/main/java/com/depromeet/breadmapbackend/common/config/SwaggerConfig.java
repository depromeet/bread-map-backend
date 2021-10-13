package com.depromeet.breadmapbackend.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
    dev, ebdev 환경에서만 Swagger 동작하도록 설정
    run configuration의 VM option: -Dspring.profiles.active=dev or ebdev 설정 필요
 */
@Configuration
@Profile({"ebdev", "dev"})
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${swagger-info.title}")
    private String swaggerTitle;
    @Value("${swagger-info.description}")
    private String swaggerDescription;
    @Value("${swagger-info.version}")
    private String swaggerVersion;

    @Bean
    public Docket restAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.depromeet.breadmapbackend")) // 대상 패키지 설정
                .paths(PathSelectors.any()) // 모든 path에 대해 swagger 설정(security 필터 사용 시, swagger permitAll() 필요) TODO security 설정 시 해당 부분에 대해서는 예외 처리 필요
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .version(swaggerVersion)
                .description(swaggerDescription)
                .build();
    }

}
