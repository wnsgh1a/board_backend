package com.example.board.repository;

import com.example.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    // 이메일이 존재하는지(true/false) 확인
    boolean existsByEmail(String email);

    // 💡 [NEW] 이메일로 실제 유저 데이터 통째로 찾아오기!
    Optional<User> findByEmail(String email);
}
