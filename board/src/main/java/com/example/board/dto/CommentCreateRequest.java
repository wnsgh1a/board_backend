package com.example.board.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private Long userId; // 댓글 작성자 번호
    private String content; // 댓글 내용
}