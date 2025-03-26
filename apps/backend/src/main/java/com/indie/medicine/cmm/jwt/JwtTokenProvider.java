package com.indie.medicine.cmm.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.indie.medicine.user.domain.Member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @description JWT 토큰 생성 및 검증 클래스
 * @packageName com.indie.medicine.cmm.jwt
 * @class JwtTokenProvider.java
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

@Component // 스프링 빈으로 등록
public class JwtTokenProvider {

    private static UserDetailsService userDetailsService; // 사용자 정보를 조회하는 서비스

    @Value("${spring.jwt.key}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expiration.access}")
    private long ACCESS_EXPIRATION_TIME;

    @Value("${spring.jwt.expiration.refresh}")
    private long REFRESH_EXPIRATION_TIME;

    /**
     *
     * @description JWT 토큰 생성
     * @author 개발2팀 정수환
     * @since 2025-03-20
     * @param member 사용자 정보
     * @return JWT 토큰
     */
    public String generateToken(Member member) {
        return JWT.create()
                .withSubject(member.getId().toString()) // 토큰 제목에 사용자 ID 설정
                .withClaim("email", member.getEmail()) // 클레임에 이메일 설정
                .withClaim("role", member.getRole().getName()) // 클레임에 권한 설정
                .withIssuedAt(new Date()) // 발급 일자
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME)) // 만료 시간
                .sign(Algorithm.HMAC512(SECRET_KEY)); // 비밀 키로 서명
    }

    /**
     *
     * @description 리프레시 토큰 생성 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param member 사용자 정보
     * @return 리프레시 토큰
     */
    public String generateRefreshToken(Member member) {
        return JWT.create()
                .withSubject(member.getId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    /**
     *
     * @description JWT 토큰에서 사용자 ID 추출 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }

    /**
     *
     * @description JWT 토큰에서 클레임 추출 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param token JWT 토큰
     * @param claim 클레임
     * @return 클레임 값
     */
    public String getClaim(String token, String claim) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)) // 비밀 키로 토큰 검증
                .build() // JWT 객체 생성
                .verify(token) // 토큰 검증
                .getClaim(claim) // 클레임 추출
                .toString();
    }

    /**
     *
     * @description JWT 토큰에서 전체 클레임 추출 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param token JWT 토큰
     * @return 클레임 맵
     */
    public Map getClaims(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)) // 비밀 키로 토큰 검증
                .build() // JWT 객체 생성
                .verify(token) // 토큰 검증
                .getClaims(); // 전체 클레임 추출
    }

    /**
     * @description Authentication 객체 생성 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-24
     * @param id 사용자 ID
     * @param role 사용자 권한
     * @return Authentication 객체
     */
    public Authentication getAuthentication(String id, String role) {
        return new UsernamePasswordAuthenticationToken(
                id, // principal (사용자 ID)
                null, // credentials (비밀번호) - 사용하지 않음(JWT 토큰으로 인증)
                Collections.singletonList(new SimpleGrantedAuthority(role)) // authorities (권한)
        );
    }

    /**
     *
     * @description JWT 토큰 검증
     * @author 개발2팀 정수환
     * @since 2025-03-20
     * @param token JWT 토큰
     * @return 토큰 유효 여부
     */
    public boolean validateToken(String token) {
        JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token);
        return true; // 토큰이 유효함
    }
}
