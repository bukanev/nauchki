package com.example.nauchki.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.nauchki.model.User;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class JwtProvider {

    final static String BEARER_PREFIX = "Bearer ";
    private static final long TIMEOUT_ACCESS = 1000L * 60 * 60 * 24;

    private final String key;

    private final JWTVerifier verifier;
    private final Algorithm algorithm;

    @Autowired
    public JwtProvider(@Value("${my-config.auth.secret}")String secret) {
        this.key = secret;
        this.algorithm = Algorithm.HMAC256(key);
        this.verifier = JWT.require(algorithm).build();
    }


    public String createToken(Authentication authentication) {
        final List<String> roleNames = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());
//        System.out.println("-----: " + authentication.getDetails());
        return JWT.create()
                .withSubject("Nauchki")
                .withClaim("Email", authentication.getName())
                .withClaim("roles", roleNames)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + TIMEOUT_ACCESS))
                .sign(algorithm);
    }
    public String createToken(User user) {
        final List<String> roleNames = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList());
        return JWT.create()
                .withSubject("Nauchki")
                .withClaim("username",user.getUsername())
                .withClaim("Email",user.getEmail())
                .withClaim("Number",user.getNumber())
                .withClaim("Login",user.getLogin())
                .withClaim("roles", roleNames)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + TIMEOUT_ACCESS))
                .sign(algorithm);
    }


    public String getUsername(String token) {
            final DecodedJWT decodedJwt = verifier.verify(token);
            return decodedJwt.getClaim("username").toString();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        final DecodedJWT decodedJwt = verifier.verify(token);
        final List<String> roleNames = decodedJwt.getClaim("roles").asList(String.class);
        return roleNames.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNullOrEmpty(header) || !header.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return header.replace(BEARER_PREFIX, "");
    }
}
