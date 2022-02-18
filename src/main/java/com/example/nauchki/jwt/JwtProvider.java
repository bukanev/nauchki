package com.example.nauchki.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    final static String BEARER_PREFIX = "Bearer ";
    private final static String KEY = "securesecuresecuresecuresecuresecuresecureNauchki";

    public String createToken(Authentication authentication) {
        return BEARER_PREFIX +  Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(10800000)))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
    }

    public String createTokenOnMonth(Authentication authentication) {
        return BEARER_PREFIX +  Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate().plusMonths(1)))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        return ((List<Map<String, String>>) Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody()
                .get("authorities"))
                .stream()
                .map(map -> new SimpleGrantedAuthority(map.get("authority")))
                .collect(Collectors.toList());
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNullOrEmpty(header) || !header.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return header.replace(BEARER_PREFIX, "");
    }
}
