package com.example.nauchki.jwt;

import com.example.nauchki.jwt.exception.TokenInvalidException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import static com.example.nauchki.jwt.JwtProvider.BEARER_PREFIX;

@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtProvider.resolveToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            String username = jwtProvider.getUsername(token);
            List<GrantedAuthority> authorities = jwtProvider.getAuthorities(token);

            response.setHeader(HttpHeaders.AUTHORIZATION,BEARER_PREFIX + token);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
        } catch (JwtException e) {
            throw new TokenInvalidException("Token is not valid");
        }
        filterChain.doFilter(request, response);
    }
}
