package com.example.board.dto;

import com.example.board.entity.Post;
import lombok.Getter;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String writer; // 🚨 유저 정보 통째로(비밀번호 포함) 말고, 닉네임만 담을 겁니다!
    private LocalDateTime createdAt;

    // Post 엔티티를 안전한 DTO로 변환하는 마법의 상자
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getUser().getNickname(); // 👈 유저 객체 안에서 닉네임만 쏙 빼옵니다!
        this.createdAt = post.getCreatedAt();
    }
}