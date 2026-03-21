package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 💡 "이제부터 스프링 보안은 내가 통제한다!"
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API에서는 필요 없는 기능 끄기 (포스트맨 에러 방지)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 🚨 "일단 지금은 모든 요청 다 통과시켜 줘!" (나중에 바꿀 예정)
                );

        return http.build();
    }

    // [NEW] 비밀번호를 안전하게 암호화해 주는 기계 빈(Bean) 등록!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}