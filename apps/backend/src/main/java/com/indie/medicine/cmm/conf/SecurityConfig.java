package com.indie.medicine.cmm.conf;

import com.indie.medicine.cmm.jwt.JwtAuthenticationFilter;
import com.indie.medicine.cmm.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/**
 * @description 시큐리티 설정 클래스
 * @packageName com.indie.medicine.cmm.conf
 * @class SecurityConfig.java
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
@Configuration
@EnableWebSecurity // Spring Security 설정을 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 생성 및 검증 객체

    /**
     * @description 시큐리티 필터 체인 빈 등록 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-24
     * @param http
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF 보안 기능 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .formLogin(login -> login.disable()) // 폼 로그인 기능 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP 기본 인증 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 관리 정책 설정
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll() // /auth/로 시작하는 URL은 인증 없이 접근 가능
                    .anyRequest().authenticated() // 그 외의 URL은 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .build();
    }

    /**
     * @description
     * @author 개발2팀 정수환
     * @since 2025-03-24
     * @param config
     * @return
     * @throws
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * @description CORS 설정 빈 등록 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-24
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration(); // CORS 설정 객체 생성
        config.setAllowedOrigins(List.of("http://localhost:8081")); // 프론트 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 요청 허용 메서드
        config.setAllowCredentials(true); // 헤더에 JWT 토큰 보내는 거 허용
        config.setAllowedHeaders(List.of("*")); // 모든 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // URL 별로 CORS 설정을 적용
        source.registerCorsConfiguration("/**", config); // 모든 URL에 대해 CORS 설정 적용

        return source;
    }

    /**
     * @description 비밀번호 암호화 빈 등록 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-20
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder 객체를 생성하여 반환
    }

}
