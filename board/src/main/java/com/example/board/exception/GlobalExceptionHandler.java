package com.example.board.exception;

import com.example.board.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 💡 핵심 마법! "프로젝트 전체 컨트롤러의 에러는 내가 다 잡는다!"
public class GlobalExceptionHandler {

    // IllegalArgumentException이 터지면 무조건 이 메서드가 실행됩니다!
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {

        // 1. 아까 만든 에러 바구니에 상태 코드 400(잘못된 요청)과 에러 메시지를 담습니다.
        // e.getMessage() 안에는 우리가 서비스에서 적어둔 "게시글이 존재하지 않습니다."가 들어있어요!
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        // 2. HTTP 상태 코드도 400으로 세팅해서 프론트엔드로 쏴줍니다!
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}