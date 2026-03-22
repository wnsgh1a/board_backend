package com.example.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // 💡 [NEW] 문지기를 섭외하기 위한 롬복 마법!
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter; // 💡 [NEW] 우리가 고용한 문지기 등판!

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 1. 회원가입과 로그인은 팔찌가 없는 사람도 해야 하니까 프리패스!
                        .requestMatchers("/api/users/signup", "/api/users/login").permitAll()
                        // 🔓 2. 게시판 구경(조회 GET 요청)은 지나가는 사람도 볼 수 있게 프리패스!
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        // 🔒 3. 글 쓰기, 수정, 삭제 등 그 외의 모든 행동은 무조건 팔찌 검사!!!
                        .anyRequest().authenticated()
                )
                // 💡 [NEW] 스프링의 기본 검사소에 도착하기 전에, 우리 문지기(JwtAuthFilter)가 먼저 팔찌를 검사하도록 맨 앞에 세워둡니다!
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}