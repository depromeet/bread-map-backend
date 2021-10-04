package com.depromeet.breadmapbackend.common.config;

import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@Profile({"!prod"}) //TODO prod 환경에서 swagger가 동작하지 않도록 하기 위한 옵션(profile prod 지정 시, 404 에러 발생할 것이므로 에러 핸들링 필요)
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
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(200).message("OK").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("Server Error").build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("No Page").build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.depromeet.breadmapbackend")) // 대상 패키지 설정
                .paths(PathSelectors.any()) // 모든 path에 대해 swagger 설정(security 필터 사용 시, swagger permitAll() 필요) TODO security 설정 시 해당 부분에 대해서는 예외 처리 필요
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .version(swaggerVersion)
                .description(swaggerDescription)
                .build();
    }

}
