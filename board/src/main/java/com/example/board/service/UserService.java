package com.example.board.service;

import com.example.board.dto.UserCreateRequest;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 1. 유저 생성 로직 (회원가입)
    public User createUser(UserCreateRequest request) {
        User user = request.toEntity();

        return userRepository.save(user);
    }
}