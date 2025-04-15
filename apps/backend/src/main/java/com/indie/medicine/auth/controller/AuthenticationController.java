package com.indie.medicine.auth.controller;

import com.indie.medicine.auth.dto.LoginDTO;
import com.indie.medicine.auth.dto.LoginResponseDTO;
import com.indie.medicine.auth.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @description 사용자 인증 컨트롤러
 * @packageName com.indie.medicine.auth.controller
 * @class AuthenticationController.java
 * @author 개발2팀 정수환
 * @since 2025-03-27
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 * 수정일        수정자          수정내용
 * ----------   --------   ---------------------------
 * 2025-03-27	정수환         최초 생성
 *
 */
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
        return ResponseEntity.ok(authenticationService.authenticate(loginDTO)); // 로그인 성공 시 토큰 반환
    }

    /**
     *
     * @description 액세스 토큰 갱신 컨트롤러
     * @author 개발2팀 정수환
     * @since 2025-03-25
     * @param header HTTP 요청 헤더
     * @return ResponseEntity<TokenResponse> 토큰 응답 객체
     */
    @Operation(summary = "액세스 토큰 갱신", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshAccessToken(@Parameter(hidden = true) @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok(authenticationService.renewAccessToken(header.substring(7))); // 액세스 토큰 갱신 성공 시 토큰 반환
    }

    /**
     * @description 로그아웃 컨트롤러
     * @author 개발2팀 정수환
     * @since 2025-03-26
     * @param header HTTP Authorization 헤더
     * @return void
     */
    @Operation(summary = "로그아웃", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/logout")
    public void logout(@Parameter(hidden = true) @RequestHeader("Authorization") String header) {
        authenticationService.logout(header.substring(7)); // 로그아웃
    }

}
