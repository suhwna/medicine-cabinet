package com.indie.medicine.user.domain;

import jakarta.persistence.*;

import lombok.Getter;

import java.time.LocalDateTime;

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

