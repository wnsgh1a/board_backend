package com.example.board.service;

import com.example.board.dto.UserCreateRequest;
import com.example.board.dto.UserResponse;
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

    // 2. 유저 단건 조회 로직
    public UserResponse getUser(Long id) {
        // DB에서 id 번호로 유저를 찾습니다.
        // 만약 그 번호의 유저가 없으면 "못 찾겠다"고 에러를 퉤 뱉어냅니다.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. id=" + id));

        // 찾은 유저 데이터를 아까 만든 '안전한 바구니'에 옮겨 담아서 반환합니다.
        return new UserResponse(user);
    }
}