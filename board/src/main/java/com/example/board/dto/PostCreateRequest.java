package com.example.board.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;

@Getter
public class PostCreateRequest {
    // 🚨 userId는 완벽하게 삭제되었습니다!

    @NotBlank(message = "제목은 텅 빌 수 없습니다! 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용은 텅 빌 수 없습니다! 꿀잼 내용을 적어주세요.")
    private String content;
}