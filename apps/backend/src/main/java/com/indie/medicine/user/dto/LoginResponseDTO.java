package com.indie.medicine.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @description 로그인 응답 DTO 클래스
 * @packageName com.indie.medicine.user.dto
 * @class LoginResponse.java
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
@Data
@Builder
public class LoginResponseDTO {
    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰
}
