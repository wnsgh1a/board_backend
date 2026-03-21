package com.example.board.repository;

import com.example.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    // 💡 [NEW] 마법의 쿼리 메서드: "이메일이 DB에 존재하는지(true/false) 찾아와!"
    boolean existsByEmail(String email);
}
