package com.example.nauchki.service;


import com.example.nauchki.model.Role;
import com.example.nauchki.model.User;
import com.example.nauchki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username '%s' not found", username));
        }
        //UserDetails userDetails = new User(user.getUsername(), user.getPassword(),
        //        mapRolesToAuthorities(user.getGrantedAuthorities()));
        return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getGrantedAuthorities()));
    }*/

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(String.format("Email '%s' not found", email));
        }
        return  new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
                mapRolesToAuthorities(user.get().getGrantedAuthorities()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
    }

}
