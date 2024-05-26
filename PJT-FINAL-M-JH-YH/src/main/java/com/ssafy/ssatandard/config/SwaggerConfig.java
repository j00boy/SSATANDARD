package com.ssafy.ssatandard.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Swagger 관련 설정을 담당하는 클래스
 */ 
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 객체를 설정하여 Swagger 문서의 정보를 정의
     * @return OpenAPI 객체
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SSATANDARD REST API")
                        .description("운동용품을 위주로 판매하는 쇼핑몰인 SSATANDARD REST API입니다. 우리는 유통이 아닌 우리의 제품을 판매합니다.")
                        .version("0.0.1")
                        .license(new License().name("SSAFY").url("https://www.ssafy.com"))
                        .contact(new Contact().name("SSATANDARD Support").url("https://www.ssafy.com")))
                .servers(List.of(
                        new Server().url("https://www.ssafy.com").description("Local server"),
                        new Server().url("https://www.ssafy.com").description("Frontend server")
                ));
    }
}
