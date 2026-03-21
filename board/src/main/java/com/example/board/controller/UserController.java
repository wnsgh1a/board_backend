package com.example.board.controller;

import com.example.board.dto.UserResponse;
import com.example.board.dto.UserSignUpRequest;
import com.example.board.service.UserService;
import jakarta.validation.Valid; // 💡 입구 컷 도구
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // [MODIFIED] 허술했던 기존 생성 API를 강력한 회원가입 API로 변경!
    // 주소: http://localhost:8080/api/users/signup
    @PostMapping("/signup")
    public String signUp(@Valid @RequestBody UserSignUpRequest request) {
        return userService.signUp(request);
    }

    // 유저 조회 API (기존 코드 그대로 유지!)
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}