package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // [NEW] 게시글 입장에서 1:N 관계로 댓글들을 품습니다!
    // mappedBy = "post" : Comment 엔티티의 'post' 필드와 연결된다는 뜻!
    // cascade, orphanRemoval : 게시글이 삭제되면 밑에 달린 댓글들도 알아서 싹 지워지는 무시무시한(편리한) 마법!
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}