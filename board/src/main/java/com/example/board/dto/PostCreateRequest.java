package com.example.board.dto;

import lombok.Getter;

@Getter
public class PostCreateRequest {
    private Long userId;    // "누가 썼는지" 알려주는 핵심 키!
    private String title;   // 글 제목
    private String content; // 글 내용
}