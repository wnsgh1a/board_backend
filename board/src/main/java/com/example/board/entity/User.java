package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity // "이 클래스는 DB 테이블과 1:1로 매칭되는 클래스야!"
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    // 💡 [NEW] 스프링 시큐리티가 "누구냐 넌!" 할 때 보여줄 신분증(권한) 필드입니다.
    // 가입하는 모든 사람은 기본적으로 "일반 유저(ROLE_USER)" 등급을 받게 됩니다.
    @Column(nullable = false)
    private String role = "ROLE_USER";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}