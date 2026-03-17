package com.example.board.dto;

import com.example.board.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private String writer; // 🚨 유저 통째로 말고 닉네임만!
    private LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getUser().getNickname(); // 👈 마법의 닉네임 빼오기
        this.createdAt = comment.getCreatedAt();
    }
}