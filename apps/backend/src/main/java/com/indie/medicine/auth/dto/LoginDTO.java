package com.indie.medicine.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 로그인 DTO 클래스
 * @packageName com.indie.medicine.user.dto
 * @class LoginDTO.java
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
public class LoginDTO {
    @Schema(description = "사용자 이메일", example = "test@example.com", required = true)
    private String email; // 사용자 이메일

    @Schema(description = "사용자 비밀번호", example = "password123", required = true)
    private String password; // 사용자 비밀번호
}
