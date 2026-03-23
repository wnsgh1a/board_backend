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
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            try {
                String token = jwtUtil.substringToken(bearerToken);
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getUserEmailFromToken(token);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // 💡 CCTV 1번: 성공하면 인텔리제이 콘솔에 출력!
                    System.out.println("✅ [성공] 토큰 인증 통과! 이메일: " + email);
                } else {
                    // 💡 CCTV 2번: 실패 이유 출력
                    System.out.println("❌ [실패] 토큰이 유효하지 않거나 만료되었습니다.");
                }
            } catch (Exception e) {
                // 💡 CCTV 3번: 파싱 에러 출력
                System.out.println("❌ [에러] 토큰 처리 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ [알림] Authorization 헤더가 없습니다.");
        }

        filterChain.doFilter(request, response);
    }
}