package com.indie.medicine.cmm.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description Redis 설정 클래스
 * @packageName com.indie.medicine.cmm.conf
 * @class RedisConfig.java
 * @author 개발2팀 정수환
 * @since 2025-03-25
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 * 수정일        수정자          수정내용
 * ----------   --------   ---------------------------
 * 2025-03-25	정수환         최초 생성
 *
 */
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    /**
     * @description Redis 연결 설정 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisHost, redisPort);
        factory.setPassword(redisPassword);
        return factory;
    }

    /**
     * @description Redis 템플릿 설정 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param connectionFactory Redis 연결 팩토리
     * @return RedisTemplate<String, String> Redis 템플릿
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>(); // RedisTemplate 객체 생성
        template.setConnectionFactory(connectionFactory); // 연결 팩토리 설정
        return template;
    }


}
