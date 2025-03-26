package com.indie.medicine.user.repository;

import com.indie.medicine.user.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @description 사용자 Repository
 * @packageName com.indie.medicine.user.repository
 * @class MemberRepository.java
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
public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByEmail(String email); // 이메일로 사용자 조회

    Optional<Member> findById(Long id); // ID로 사용자 조회
}
