package com.indie.medicine.mbr.dto;

import lombok.Data;

/**
 * @description 회원가입 DTO 클래스
 * @packageName com.indie.medicine.mbr.dto
 * @class SignupDTO.java
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
@Data
public class SignupDTO {
    private String email; // 사용자 이메일
    private String password; // 사용자 비밀번호
    private String name; // 사용자 이름
}
