package com.example.board.config;

import com.example.board.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // 💡 요청이 올 때마다 딱 한 번씩만 검사하는 문지기!

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 유저가 내민 HTTP 헤더 중에서 'Authorization' 이라는 이름의 팔찌 통을 찾습니다.
        String bearerToken = request.getHeader("Authorization");

        // 2. 팔찌 통이 존재하면 검사 시작!
        if (bearerToken != null) {
            // "Bearer " 글자 떼어내고 진짜 토큰만 추출
            String token = jwtUtil.substringToken(bearerToken);

            // 3. 팔찌가 진짜인지 감별기계로 확인!
            if (jwtUtil.validateToken(token)) {
                // 4. 진짜면 이메일을 뽑아내서 "이 사람 신분 확인됐음!" 하고 시큐리티 시스템에 임시 등록합니다.
                String email = jwtUtil.getUserEmailFromToken(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 5. 검사가 끝났으면 다음 창구로 가도록 길을 비켜줍니다. (팔찌 없으면 없는 채로 보냄 -> 뒤에서 시큐리티가 쫓아냄!)
        filterChain.doFilter(request, response);
    }
}