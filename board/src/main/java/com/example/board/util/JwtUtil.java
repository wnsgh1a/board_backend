package com.example.board.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // 💡 "스프링아, 이 팔찌 기계도 네가 관리해 줘!"
public class JwtUtil {

    // 🚨 (주의) 실무에서는 이 비밀키를 코드에 직접 쓰지 않고 application.yml 같은 곳에 꽁꽁 숨겨둡니다!
    // 지금은 테스트를 위해 넉넉하게 긴 문자열을 직접 넣겠습니다. (짧으면 에러 납니다!)
    private final String SECRET_KEY = "MySuperSecretKeyForBoardProjectMustBeLongEnough";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 팔찌 유효기간: 1시간 (밀리초 단위)

    // 팔찌(토큰) 발급 메서드
    public String createToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // 팔찌 주인의 이름(이메일)
                .claim("role", role) // 팔찌 주인의 등급(일반 유저인지, 관리자인지)
                .setIssuedAt(new Date()) // 팔찌 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 기계의 관리자 도장 쾅!
                .compact(); // 압축해서 긴 문자열로 반환!
    }
}