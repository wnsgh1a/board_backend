package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity // "이 클래스는 DB 테이블과 1:1로 매칭되는 클래스야!" 라고 알려주는 역할
@Table(name = "users") // 테이블 이름은 'users'로 할게 (user는 DB 예약어라 충돌날 수 있어서 보통 users로 씁니다)
@Getter // 롬복 마법 1: 복잡한 Getter 코드를 안 짜도 알아서 만들어줌
@NoArgsConstructor // 롬복 마법 2: 기본 생성자를 알아서 만들어줌
public class User {

    @Id // 이 녀석이 기본키(PK)다!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment (1, 2, 3... 자동 증가)
    private Long id;

    @Column(nullable = false, unique = true) // Not Null, 중복 불가
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @CreationTimestamp // 데이터가 처음 생성될 때 시간을 자동으로 찍어줌!
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // 데이터가 수정될 때 시간을 자동으로 갱신해줌!
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}