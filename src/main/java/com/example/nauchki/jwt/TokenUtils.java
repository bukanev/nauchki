package com.example.nauchki.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TokenUtils {

    public Optional<Authentication> getAuthentication(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            Optional.empty();
        }
        return Optional.of(auth);

    }


    public Optional<String> getPrincipalName(){

        Optional<Authentication> v = getAuthentication();
        if(v.isPresent()){
            Authentication a = v.get();
            return Optional.of((String)a.getPrincipal());
        }
        return Optional.empty();

    }

    public List<String> getRoles(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return new ArrayList<>();
        }
        List<SimpleGrantedAuthority> lr = (List<SimpleGrantedAuthority>) auth.getAuthorities();
        List<String> ls = new ArrayList<>();
        for (SimpleGrantedAuthority r: lr) {
            ls.add(r.getAuthority());
        }
        return ls;
    }
}
