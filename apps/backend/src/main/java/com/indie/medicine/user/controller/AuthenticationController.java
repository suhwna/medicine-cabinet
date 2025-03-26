package com.indie.medicine.user.controller;

import com.indie.medicine.user.dto.LoginDTO;
import com.indie.medicine.user.dto.LoginResponseDTO;
import com.indie.medicine.user.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService; // 사용자 인증 서비스

    /**
     *
     * @description 로그인 컨트롤러
     * @author 개발2팀 정수환
     * @since 2025-03-20
     * @param loginDTO 사용자 로그인 정보
     * @return  String 토큰
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(loginDTO)); // 로그인 성공 시 토큰 반환
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 로그인 실패 시 400 에러 반환
        }
    }

    /**
     *
     * @description 액세스 토큰 갱신 컨트롤러
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param header HTTP 요청 헤더
     * @return ResponseEntity<TokenResponse> 토큰 응답 객체
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshAccessToken(@RequestHeader("Authorization") String header) {
        try {
            return ResponseEntity.ok(authenticationService.reissueAccessToken(header)); // 액세스 토큰 갱신 성공 시 토큰 반환
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 액세스 토큰 갱신 실패 시 400 에러 반환
        }
    }

    /**
     * @description 로그아웃 컨트롤러
     * @author 개발2팀 정수환
     * @since 2025-03-26
     * @param header HTTP Authorization 헤더
     * @return void
     */
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String header) {
        try {
            String accessToken = header.replace("Bearer ", "");
            authenticationService.logout(accessToken);
            return ResponseEntity.ok().build(); // 로그아웃 성공
        } catch (Exception e) {
            return ResponseEntity.status(401).build(); // 토큰 에러 시 401
        }
    }

}
