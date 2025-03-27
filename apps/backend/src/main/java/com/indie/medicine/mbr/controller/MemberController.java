package com.indie.medicine.mbr.controller;

import com.indie.medicine.mbr.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mbr")
@RequiredArgsConstructor
public class MemberController {

    // 사용자 서비스
    private final MemberService memberService;

    // 사용자 조회 메소드



    // 회원가입 메소드

}
