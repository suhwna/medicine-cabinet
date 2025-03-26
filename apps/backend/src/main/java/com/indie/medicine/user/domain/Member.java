package com.indie.medicine.user.domain;

import jakarta.persistence.*;

import lombok.Getter;

import java.time.LocalDateTime;

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
