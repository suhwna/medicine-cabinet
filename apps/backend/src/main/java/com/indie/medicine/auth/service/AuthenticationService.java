package com.indie.medicine.auth.service;

import com.indie.medicine.cmm.jwt.JwtTokenProvider;
import com.indie.medicine.mbr.domain.Member;
import com.indie.medicine.auth.dto.LoginDTO;
import com.indie.medicine.auth.dto.LoginResponseDTO;
import com.indie.medicine.mbr.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final 필드를 파라미터로 받는 생성자를 생성
public class AuthenticationService {

    private final MemberRepository memberRepository; // 사용자 Repository

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 생성 및 검증 객체

    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 객체

    private final RedisTokenService redisTokenService; // Redis 토큰 서비스 객체

    /**
     *
     * @description 사용자 인증 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-20
     * @param loginDTO 사용자 로그인 정보
     * @throws RuntimeException
     */
    public LoginResponseDTO authenticate(LoginDTO loginDTO) {
        Member member = memberRepository.findByEmail(loginDTO.getEmail()) // 사용자 조회
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("아이디 또는 비밀번호가 올바르지 않습니다.");
                });

        if (!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())) { // 비밀번호 일치 여부 확인
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.generateRefreshToken(member);
        redisTokenService.saveRefreshToken(member.getId().toString(), token); // 리프레시 토큰 저장

        // 액세스 토큰과 리프레시 토큰 반환
        return LoginResponseDTO.builder()
                .accessToken(token)
                .refreshToken(redisTokenService.getRefreshToken(member.getId().toString()))
                .build();
    }

    /**
     *
     * @description 액세스 토큰 갱신 메서드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param refreshToken 리프레시 토큰
     * @return LoginResponseDTO 토큰 응답 객체
     */
    public LoginResponseDTO renewAccessToken(String refreshToken) {
        String id = jwtTokenProvider.getSubject(refreshToken); // 리프레시 토큰에서 사용자 ID 추출

        if (!redisTokenService.getRefreshToken(id).equals(refreshToken)) { // Redis 저장소에 저장된 리프레시 토큰과 요청된 리프레시 토큰 비교
            throw new IllegalArgumentException("리프레시 토큰이 올바르지 않습니다.");
        }

        Member member = memberRepository.findById(Long.parseLong(id)) // 사용자 조회
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
                });

        // 액세스 토큰 반환
        return LoginResponseDTO.builder()
                .accessToken(jwtTokenProvider.generateToken(member))
                .refreshToken(redisTokenService.getRefreshToken(id))
                .build();
    }

    /**
     *
     * @description 로그아웃 메소드
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param accessToken 액세스 토큰
     */
    public void logout(String accessToken) {
        String id = jwtTokenProvider.getSubject(accessToken); // 액세스 토큰에서 사용자 ID 추출
        String token = redisTokenService.getRefreshToken(id); // 리프레시 토큰 조회

        if (token == null) {
            throw new IllegalArgumentException("리프레시 토큰이 존재하지 않습니다.");
        }

        redisTokenService.deleteRefreshToken(id); // 리프레시 토큰 삭제
    }
}