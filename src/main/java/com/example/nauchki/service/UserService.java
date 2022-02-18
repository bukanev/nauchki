package com.example.nauchki.service;

import com.example.nauchki.jwt.JwtProvider;
import com.example.nauchki.model.Role;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.UserDto;
import com.example.nauchki.repository.RoleRepository;
import com.example.nauchki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final FileSaver fileSaver;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailSender mailSender;
    private final PasswordEncoder bCryptPasswordEncoder;


    public boolean saveUser(UserDto userDto) {
        if (userDto.getEmail() == null || userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return false;
        }
        saveRole();
        User user = userDto.mapToUser();
        Set<Role> roles = new HashSet<>();
        /*if (userDto.getUsername() != null) {
            if (userDto.getUsername().equals("admin") && userDto.getUsername().equals(userDto.getLogin())) {
                roles.add(new Role(2L, "ADMIN"));
            }
        }*/
        roles.add(new Role(1L, "USER"));
        user.setGrantedAuthorities(roles);
        user.setActivate(0);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (user.getEmail() != null) {
            String message = String.format(
                            "Hello! \n" +
                            "Welcome to Nauchki! To confirm your email, please, visit next link: https://nauchki.herokuapp.com/activate/%s",
                            //user.getLogin(),
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

    public UserDto getUser(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent()) {
            user.get().setPassword("PROTECTED");
            return UserDto.valueOf(user.get());
        }
        Optional<User> userTwo = userRepository.findByEmail(login);
        if(userTwo.isPresent()){
            userTwo.get().setPassword("PROTECTED");
            return UserDto.valueOf(userTwo.get());
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

    public ResponseEntity<HttpStatus> getAuth(String login, String password) {
        try {
            Optional<User> user = userRepository.findByLogin(login);
            if (user.isPresent()) {
                UserDetails userDetails = user.get();
                if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login, null, userDetails.getAuthorities());
                    authentication.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity getAuthEmail(String email, String password) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                UserDetails userDetails = user.get();
                if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
                    authentication.setDetails(userDetails);
                    String token = jwtProvider.createToken(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    HttpHeaders headers = new HttpHeaders();
                    headers.set(HttpHeaders.AUTHORIZATION, token);
                    return new ResponseEntity<>(headers,HttpStatus.OK);
                }
            }
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public String addImage(MultipartFile file, Long id) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<User> user = userRepository.findById(id);
            String path = fileSaver.saveFile(file);
            if (user.isPresent()) {
                user.get().setImg_path(path);
                userRepository.save(user.get());
                return path;
            }
        }
        return null;
    }


}
