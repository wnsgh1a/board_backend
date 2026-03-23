package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
    @NotBlank(message = "댓글 내용은 텅 빌 수 없습니다!")
    private String content;
}