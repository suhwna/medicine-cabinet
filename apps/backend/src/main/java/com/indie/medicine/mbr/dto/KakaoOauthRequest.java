package com.indie.medicine.mbr.dto;

/**
 * @description 카카오 OAuth 요청 DTO
 * @packageName com.indie.medicine.mbr.dto
 * @class KakaoOauthRequest.java
 * @author 개발2팀 정수환
 * @since 2025. 4. 9.
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 * 수정일        수정자          수정내용
 * ----------   --------   ---------------------------
 * 2025. 4. 9.	정수환         최초 생성
 *
 */
public class KakaoOauthRequest {
    /*인가 코드*/
    private String code;
    /*카카오 앱 키*/
    private String redirectUri;
}
