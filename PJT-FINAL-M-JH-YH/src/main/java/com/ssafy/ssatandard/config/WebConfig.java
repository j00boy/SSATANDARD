package com.ssafy.ssatandard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.ssatandard.interceptor.AdminInterceptor;
import com.ssafy.ssatandard.interceptor.LoginInterceptor;

/**
 * WebConfig 클래스는 웹 관련 설정을 담당
 * WebMvcConfigurer 인터페이스를 구현하여 커스터마이징된 웹 구성 요소를 정의
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * loginInterceptor 메소드는 LoginInterceptor 빈을 생성하여 반환
     */
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * adminInterceptor 메소드는 AdminInterceptor 빈을 생성하여 반환
     */
    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

    /**
     * addResourceHandlers : 정적 자원의 위치와 해당 자원에 대한 URL 매핑을 설정
     * 모든 URL 패턴 (/**)에 대해 classpath:/static/ 위치의 자원을 제공하도록 설정
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * addCorsMappings 메소드는 전역 CORS 설정을 정의
     	* http://localhost:5173 : Vue 접근
     	* https://i.imgur.com : 업로드 사진 경로
     * 에 대해 모든 HTTP 메소드(POST, GET, PUT, DELETE)를 허용
     * 자격 증명 정보를 포함할 수 있도록 설정
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "https://i.imgur.com")
                .allowedMethods("POST", "GET", "PUT", "DELETE")
                .allowCredentials(true);
    }

    /**
     * addInterceptors : 인터셉터를 등록하고 특정 경로 패턴에 대해 적용
     * loginInterceptor: /mypage/**, /cart/** 
     * adminInterceptor: /admin/**
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/mypage/**");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/cart/**");
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/admin/**");
    }
}
