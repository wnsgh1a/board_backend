package com.example.board.service;

import com.example.board.dto.UserResponse;
import com.example.board.dto.UserSignUpRequest; // 👈 새로 만든 DTO!
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // 💡 암호화 도구
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 💡 암호화 기계 출동!

    // 1. 유저 생성 로직 (진짜 실무형 회원가입으로 진화!)
    @Transactional
    public String signUp(UserSignUpRequest request) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 🚨 핵심: 비밀번호 암호화 (1234 -> $2a$10$e/어쩌구...)
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 유저 엔티티 생성 및 포장
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword); // 반드시 암호화된 비밀번호를 넣습니다!
        user.setNickname(request.getNickname());

        userRepository.save(user);

        return "회원가입이 완료되었습니다! 환영합니다 🎉";
    }

    // 2. 유저 단건 조회 로직 (기존 코드 그대로 유지!)
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. id=" + id));
        return new UserResponse(user);
    }
}