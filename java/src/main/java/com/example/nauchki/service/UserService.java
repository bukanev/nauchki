package com.example.nauchki.service;

import com.example.nauchki.model.Role;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.UserDto;
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
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MailSender mailSender;

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

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public User findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElse(null);
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
        if (userDto.getLogin() == null) {
            return false;
        }
        Optional<User> userFromDB = userRepository.findByLogin(userDto.getLogin());
        if (userFromDB.isPresent()) {
            return false;
        }
        saveRole();
        User user = userDto.mapToUser();
        List<Role> roles = new ArrayList<>();
        if (userDto.getName() != null) {
            if (userDto.getName().equals("admin") && userDto.getName().equals(userDto.getLogin())) {
                roles.add(new Role(2L, "ADMIN"));
            }
        }
        roles.add(new Role(1L, "USER"));
        user.setRoles(roles);
        user.setActivate(0);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (user.getEmail() != null) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Nauchki. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }

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

    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDto userDto = UserDto.valueOf(user.get());
            userDto.setPassword("PROTECTED");
            userDto.setSecretAnswer(user.get().getSecretAnswer());
            return userDto;
        }
        return null;
    }

    public UserDto getUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        user.setPassword("PROTECTED");
        return UserDto.valueOf(user);
    }
    public UserDto getUser(UserDto userDto) {
        Optional<User> user = userRepository.findByLogin(userDto.getLogin());
        if(user.isPresent()) {
            user.get().setPassword("PROTECTED");
            return UserDto.valueOf(user.get());
        }
        return new UserDto();
    }

    public boolean editPassword(UserDto userDto) {
        Optional<User> user = userRepository.findByLogin(userDto.getLogin());
        if (user.isPresent() &
                !userDto.getSecretAnswer().isEmpty() &
                !userDto.getPassword().isEmpty()) {
            if (bCryptPasswordEncoder.matches(user.get().getSecretAnswer(),
                    bCryptPasswordEncoder.encode(userDto.getSecretAnswer()))) {
                user.get().setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
                userRepository.save(user.get());
                return true;
            }
        }
        return false;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
