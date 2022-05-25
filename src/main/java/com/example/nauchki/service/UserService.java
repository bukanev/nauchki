package com.example.nauchki.service;

import com.example.nauchki.exceptions.ResourceNotFoundException;
import com.example.nauchki.jwt.JwtProvider;
import com.example.nauchki.model.Role;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.UserDto;
import com.example.nauchki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final String url;
    private final JwtProvider jwtProvider;
    private final FileService fileSaver;
    private final UserRepository userRepository;
    private final MailSender mailSender;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(@Value("${my-config.url}")String url ,
                       JwtProvider jwtProvider,
                       FileService fileSaver,
                       UserRepository userRepository,
                       MailSender mailSender,
                       PasswordEncoder bCryptPasswordEncoder) {
        this.url = url;
        this.jwtProvider = jwtProvider;
        this.fileSaver = fileSaver;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Сохранение нового пользователя
     *
     * @param userDto
     * @return
     */
    public boolean saveUser(UserDto userDto) {
        if (userDto.getEmail() == null || userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return false;
        }
        //saveRole();
        User user = userDto.mapToUser();
        Set<Role> roles = new HashSet<>();
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
                            "Welcome to Nauchki! To confirm your email, please, visit next link: " + url+":8080" + "/activate/%s",
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    /*private void saveRole() {
        if (roleRepository.findByName("USER") == null) {
            roleRepository.save(new Role(1L, "USER"));
        }
        if (roleRepository.findByName("ADMIN") == null) {
            roleRepository.save(new Role(2L, "ADMIN"));
        }
    }*/


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

    public UserDto getUser(String email) {
        Optional<User> userTwo = userRepository.findByEmail(email);
        if (userTwo.isPresent()) {
            userTwo.get().setPassword("PROTECTED");
            return UserDto.valueOf(userTwo.get());
        }
        return new UserDto();
    }

    public boolean editPassword(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent()) {
            user.get().setActivationCode(UUID.randomUUID().toString());
            String message = String.format(
                    //TODO изменить url после деплоя фронта
                    "Для смены пароля пройдите по ссылке: " + url + ":3000" + "/editpassword/%s",
                    user.get().getActivationCode()
            );
            userRepository.save(user.get());
            mailSender.send(userDto.getEmail(), "Смена пароля", message);
            return true;
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

    public ResponseEntity<HttpStatus> getAuthLogin(String login, String password) {
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
                    return new ResponseEntity<>(token, headers, HttpStatus.OK);
                }
            }
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Transactional
    public String changeImage(MultipartFile file, Long fileId, Principal principal) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<User> user = userRepository.findByEmail(principal.getName());
            User userModel = user.orElseThrow(()->new ResourceNotFoundException("User '" + principal.getName() + "' not found"));
            boolean fileConsists = userModel.getFiles().stream()
                    .anyMatch(v-> v.getId()==fileId);
            if(!fileConsists){
                throw new ResourceNotFoundException("File with id '" + fileId + "' not belong to user '" + principal.getName() + "' not found");
            }
            String path = fileSaver.changeFile(file, fileId);
            userRepository.save(userModel);
            return path;
        }
        return null;
    }

    @Transactional
    public String addImage(MultipartFile file, Principal principal) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<User> user = userRepository.findByEmail(principal.getName());
            User userModel = user.orElseThrow(()->new ResourceNotFoundException("User '" + principal.getName() + "' not found"));
            String path = fileSaver.saveAttachedFile(file, userModel);
            if(userModel.getFiles().size()==1){
                userModel.setBaseImageId(userModel.getFiles().get(0).getId());
            }
            userRepository.save(userModel);
            return path;
        }
        return null;
    }

    @Transactional
    public String addImage(MultipartFile file, Principal principal, String tags, String description) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<User> user = userRepository.findByEmail(principal.getName());
            User userModel = user.orElseThrow(()->new ResourceNotFoundException("User '" + principal.getName() + "' not found"));
            String path = fileSaver.saveAttachedFile(file, userModel, tags, description);
            userRepository.save(userModel);
            return path;
        }
        return null;
    }

    @Transactional
    public Long setBaseImage(Long fileId, Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        User userModel = user.orElseThrow(()->new ResourceNotFoundException("User '" + principal.getName() + "' not found"));
        boolean fileConsists = userModel.getFiles().stream()
                .anyMatch(v-> v.getId()==fileId);
        if(!fileConsists){
            throw new ResourceNotFoundException("File with id '" + fileId + "' not belong to user '" + principal.getName() + "' not found");
        }
        userModel.setBaseImageId(fileId);
        userRepository.save(userModel);
        return fileId;
    }

    @Transactional
    public boolean deleteImg(Principal principal, Long fileId){
        Optional<User> user = userRepository.findByEmail(principal.getName());
        User userModel = user.orElseThrow(()->new ResourceNotFoundException("User '" + principal.getName() + "' not found"));
        boolean fileConsists = userModel.getFiles().stream()
                .anyMatch(v-> v.getId()==fileId);
        if(!fileConsists){
            throw new ResourceNotFoundException("File with id '" + fileId + "' not belong to user '" + principal.getName() + "' not found");
        }
        fileSaver.deleteFile(fileId);
        return true;
    }

    @Transactional
    public boolean deleteImg(Principal principal){
        Optional<User> user = userRepository.findByEmail(principal.getName());
        User userModel = user.orElseThrow(()->new ResourceNotFoundException("User '" + principal.getName() + "' not found"));
        Long fileId = userModel.getBaseImageId();
        if(fileId==null || fileId==0){
            return false;
        }
        boolean fileConsists = userModel.getFiles().stream()
                .anyMatch(v-> v.getId()==fileId);
        if(!fileConsists){
            throw new ResourceNotFoundException("File with id '" + fileId + "' not belong to user '" + principal.getName() + "' not found");
        }
        userModel.setBaseImageId(0L);
        userRepository.save(userModel);
        fileSaver.deleteFile(fileId);
        return true;
    }

    @Transactional
    public boolean deleteAllImages(Principal principal){
        Optional<User> user = userRepository.findByEmail(principal.getName());
        User userModel = user.orElseThrow(()->new ResourceNotFoundException("User '" + principal.getName() + "' not found"));
        if(fileSaver.deleteAllAttachedFiles(userModel)){
            userModel.setBaseImageId(0L);
            userRepository.save(userModel);
            return true;
        }
        return false;
    }


    public boolean editPass(String code, String password) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    public boolean editPass(UserDto userDto) {
        User user = userRepository.findByActivationCode(userDto.getActivationCode());
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return true;
    }
}
