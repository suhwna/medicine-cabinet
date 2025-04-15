package com.indie.medicine.cmm.conf;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description  Swagger 설정 클래스
 * @packageName com.indie.medicine.cmm.conf
 * @class SwaggerConfig.java
 * @author 개발2팀 정수환
 * @since 2025-03-20
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 * 수정일        수정자          수정내용
 * ----------   --------   ---------------------------
 * 2025-03-20	정수환         최초 생성
 *
 */
@Configuration // 스프링 설정 클래스
public class SwaggerConfig {

    /**
     *
     * @description Swagger 설정 메서드
     * @author 개발2팀 정수환
     * @since 2025. 3. 18.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()  // OpenAPI 객체 생성
                .info(new Info() // Info 객체 생성
                        .title("약 상자 API") // API 제목
                        .version("1.0") // API 버전
                        .description("약 상자 앱의 API 문서") // API 설명
                        .termsOfService("http://swagger.io/terms/") // 서비스 약관
                        .contact(new io.swagger.v3.oas.models.info.Contact().name("정수환")) // 연락처
                        .license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0") // 라이선스
                                .url("http://springdoc.org"))) // 라이선스 URL
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // 보안 요구 사항 추가
                .components(new Components() // Components 객체 생성
                    .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
    }
}
