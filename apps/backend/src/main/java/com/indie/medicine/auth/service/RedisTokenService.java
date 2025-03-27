package com.indie.medicine.auth.service;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description Redis 토큰 서비스 클래스
 * @packageName com.indie.medicine.auth.service
 * @class RedisTokenService.java
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
@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    /**
     *
     * @description 리프레시 토큰 저장 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param id 사용자 아이디
     * @param refreshToken 리프레시 토큰
     * @return void
     */
    public void saveRefreshToken(String id, String refreshToken) {
        String key = "refresh:" + id; // 키 생성
        redisTemplate.opsForValue().set(
                key,
                refreshToken, // 토큰 값
                REFRESH_TOKEN_TTL // 만료 시간
        );
    }

    /**
     *
     * @description 리프레시 토큰 조회 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param id 사용자 아이디
     * @return 리프레시 토큰
     */
    public String getRefreshToken(String id) {
        String key = "refresh:" + id;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     *
     * @description 리프레시 토큰 삭제 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param id 사용자 아이디
     * @return void
     */
    public void deleteRefreshToken(String id) {
        redisTemplate.delete("refresh:" + id);
    }

    /**
     *
     * @description 리프레시 토큰 유효성 검사 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param id 사용자 아이디
     * @param providedToken 제공된 토큰
     * @return 유효 여부
     */
    public boolean isValid(String id, String providedToken) {
        String stored = getRefreshToken(id);
        return stored != null && stored.equals(providedToken);
    }
}

