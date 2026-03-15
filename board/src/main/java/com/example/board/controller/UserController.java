package com.example.board.controller;

import com.example.board.dto.UserCreateRequest;
import com.example.board.dto.UserResponse;
import com.example.board.entity.User;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    // 유저 조회 API (GET 요청)
    // 주소 예시: http://localhost:8080/api/users/1
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        // @PathVariable: URL 주소에 있는 {id} 숫자를 쏙 뽑아서 변수로 만들어줍니다.
        return userService.getUser(id);
    }
}