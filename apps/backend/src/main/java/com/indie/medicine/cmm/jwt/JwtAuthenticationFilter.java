package com.indie.medicine.cmm.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @description JWT 인증 필터
 * @packageName com.indie.medicine.cmm.jwt
 * @class JwtAuthenticationFilter.java
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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 생성 및 검증 유틸 클래스

    /**
     *
     * @description JWT 토큰 검증 및 사용자 인증 설정 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-20
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param filterChain 필터 체인 객체
     * @return void
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI(); // 요청 URL

        // 토큰 검증 스킵 여부 판단
        if (shouldSkip(path, request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtFromRequest(request); // 토큰 추출

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                String id = jwtTokenProvider.getSubject(token); // 토큰에서 사용자 ID 추출
                String role = jwtTokenProvider.getClaim(token, "role"); // 토큰에서 권한 추출
                Authentication authentication = jwtTokenProvider.getAuthentication(id, role); // 사용자 인증 객체 생성
                SecurityContextHolder.getContext().setAuthentication(authentication); // 사용자 인증 설정
            } else {
                SecurityContextHolder.clearContext(); // 사용자 인증 정보 제거
            }
        } catch (TokenExpiredException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인해주세요.");
            return;
        } catch (JWTVerificationException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return;
        }

        filterChain.doFilter(request, response); // 다음 필터로 요청 전달
    }

    /**
     *
     * @description JWT 토큰 검증 스킵 여부 판단 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-26
     * @param path 요청 URL
     * @param request HTTP 요청 객체
     * @return boolean 스킵 여부
     */
    private boolean shouldSkip(String path, HttpServletRequest request) {
        return path.startsWith("/auth/") || "OPTIONS".equalsIgnoreCase(request.getMethod()); // /auth/로 시작하는 URL과 OPTIONS 메서드는 스킵
    }

    /**
     *
     * @description HTTP 요청 객체에서 JWT 토큰 추출 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-20
     * @param request HTTP 요청 객체
     * @return JWT 토큰 | null
     * @throws ServletException
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Authorization 헤더에서 토큰 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // Bearer 토큰인 경우
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 반환
        }
        return null;
    }
}
