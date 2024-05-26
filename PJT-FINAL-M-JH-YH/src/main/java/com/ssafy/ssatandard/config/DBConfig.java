package com.ssafy.ssatandard.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration : Spring IoC 컨테이너에서 빈을 정의할 것임을 나타냅니다.
 * 
 * @MapperScan : MyBatis Mapper 인터페이스를 스캔할 패키지를 지정
 * basePackages : 지정된 패키지에서 Mapper 인터페이스를 찾아 스프링 빈으로 등록
 */
@Configuration
@MapperScan(basePackages = "com.ssafy.ssatandard.model.dao")
public class DBConfig {

}
