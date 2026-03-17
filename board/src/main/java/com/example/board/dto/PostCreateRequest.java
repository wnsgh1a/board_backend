package com.example.board.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;

@Getter
public class PostCreateRequest {
    private Long userId;

    // 💡 마법: 띄어쓰기만 있거나 아예 비어있는 걸 용납하지 않습니다!
    @NotBlank(message = "제목은 텅 빌 수 없습니다! 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용은 텅 빌 수 없습니다! 꿀잼 내용을 적어주세요.")
    private String content;
}