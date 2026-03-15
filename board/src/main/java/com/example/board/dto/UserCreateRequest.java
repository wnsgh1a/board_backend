package com.example.board.dto;

import com.example.board.entity.User;
import lombok.Getter;

@Getter
public class UserCreateRequest {
    private String email;
    private String password;
    private String nickname;

    public User toEntity() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNickname(this.nickname);
        return user;
    }
}
