package com.example.board.dto;

import com.example.board.entity.Post;
import lombok.Getter;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String writer; // 🚨 유저 정보 통째로(비밀번호 포함) 말고, 닉네임만 담을 겁니다!
    private LocalDateTime createdAt;
    // [NEW] 댓글들을 담아서 내보낼 리스트 추가!
    private List<CommentResponse> comments;

    // Post 엔티티를 안전한 DTO로 변환하는 마법의 상자
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getUser().getNickname(); // 👈 유저 객체 안에서 닉네임만 쏙 빼옵니다!
        this.createdAt = post.getCreatedAt();
        // [NEW] 날것의 엔티티(Comment) 리스트를 안전한 바구니(CommentResponse) 리스트로 싹 바꿔서 담습니다!
        this.comments = post.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}