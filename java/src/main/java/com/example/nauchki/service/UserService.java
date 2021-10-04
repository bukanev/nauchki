package com.example.nauchki.service;

import com.example.nauchki.model.Role;
import com.example.nauchki.model.User;
import com.example.nauchki.model.UserDto;
import com.example.nauchki.repository.RoleRepository;
import com.example.nauchki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public boolean saveUser(UserDto userDto) {
        if(userDto.getLogin() == null) {
            return false;
        }
        User userFromDB = userRepository.findByLogin(userDto.getLogin());
        if (userFromDB != null) {
            return false;
        }
        saveRole();
        User user = userDto.mapToUser();
        List<Role> roles = new ArrayList<>();
        if (userDto.getName().equalsIgnoreCase("admin") && userDto.getName().equalsIgnoreCase(userDto.getLogin())) {
            roles.add(new Role(2L, "ADMIN"));
        }
        roles.add(new Role(1L, "USER"));
        user.setRoles(roles);
        user.setScore(10);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    private void saveRole() {
        if (roleRepository.findByName("USER") == null) {
            roleRepository.save(new Role(1L, "USER"));
        }
        if (roleRepository.findByName("ADMIN") == null) {
            roleRepository.save(new Role(2L, "ADMIN"));
        }
    }


    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public UserDto getUser(Long id, Principal principal) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent() & (principal.getName().equalsIgnoreCase(user.get().getUsername()) | principal.getName().equals("ADMIN"))){
            return UserDto.valueOf(user.get());
        }
        return null;
    }
}
