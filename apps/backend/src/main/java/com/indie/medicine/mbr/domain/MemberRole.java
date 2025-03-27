package com.indie.medicine.mbr.domain;

import jakarta.persistence.*;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @description 사용자 권한 도메인 클래스
 * @packageName com.indie.medicine.mbr.domain
 * @class MemberRole.java
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
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // ex: USER, ADMIN

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}

