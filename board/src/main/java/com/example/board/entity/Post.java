package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여기가 핵심! User(1) : Post(N) 관계를 맺어줍니다.
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 (성능 최적화를 위한 필수 설정!)
    @JoinColumn(name = "user_id", nullable = false) // DB 테이블에 들어갈 외래키(FK) 컬럼 이름
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // 긴 글을 넣기 위해 TEXT 타입으로 지정
    private String content;

    @Column(name = "view_count")
    private int viewCount = 0; // 조회수 기본값 0

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}