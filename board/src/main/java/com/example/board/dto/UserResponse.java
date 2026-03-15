package com.example.board.dto;

import com.example.board.entity.User;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    // 🚨 핵심: password 필드는 아예 만들지도 않습니다!

    // User 엔티티(날것의 데이터)를 받아서 안전한 DTO로 변환해주는 생성자
    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.createdAt = user.getCreatedAt();
    }
}