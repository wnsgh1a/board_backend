package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 💡 생성자를 자동으로 만들어주는 롬복 마법!
public class ErrorResponse {
    private int status;     // 에러 코드 (예: 400, 404)
    private String message; // 에러 메시지 (예: "게시글이 존재하지 않습니다.")
}