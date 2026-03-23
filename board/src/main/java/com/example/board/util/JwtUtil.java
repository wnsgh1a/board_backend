package com.example.board.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "MySuperSecretKeyForBoardProjectMustBeLongEnough";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    public String createToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String substringToken(String tokenValue) {
        if (tokenValue != null && tokenValue.startsWith("Bearer ")) {
            // 💡 핵심: 뒤에 .trim()을 붙여서 복붙 실수로 들어간 공백/엔터를 완벽하게 제거합니다!
            return tokenValue.substring(7).trim();
        }
        throw new IllegalArgumentException("토큰이 없거나 형식이 틀렸습니다.");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 💡 실패하면 뭉뚱그리지 않고 '진짜 이유'를 속 시원하게 출력!
            System.out.println("🔍 [토큰 감별 기계] 실패 원인: " + e.getMessage());
            return false;
        }
    }

    public String getUserEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}