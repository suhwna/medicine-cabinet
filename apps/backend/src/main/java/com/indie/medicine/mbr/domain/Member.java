package com.indie.medicine.mbr.domain;

import jakarta.persistence.*;

import lombok.Getter;

import java.time.LocalDateTime;


/**
 * @description 사용자 도메인 클래스
 * @packageName com.indie.medicine.mbr.domain
 * @class Member.java
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
@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 ID

    @Column(nullable = false, unique = true, length = 255)
    private String email; // 이메일

    @Column(nullable = false, length = 100)
    private String password; // 비밀번호

    @Column(nullable = false, length = 100)
    private String name; // 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id") // members 테이블에 role_id 컬럼 생성됨
    private MemberRole role; // 사용자 권한

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성일

    private LocalDateTime updatedAt; // 수정일

}
