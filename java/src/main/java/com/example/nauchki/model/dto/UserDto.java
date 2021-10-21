package com.example.nauchki.model.dto;

import com.example.nauchki.model.Role;
import com.example.nauchki.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String login;
    private String password;
    private String number;
    private String Email;
    private String secretAnswer;
    private Collection<Role> roleList;
    private String secretQuestion;
    private String activationCode;


    public UserDto(Long id, String name, String login, String password, String number, String email,Collection<Role> roleList) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.number = number;
        this.Email = email;
        this.roleList = roleList;
    }

    public static UserDto valueOf(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                user.getNumber(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public User mapToUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        user.setLogin(login);
        user.setPassword(password);
        user.setNumber(number);
        user.setEmail(Email);
        user.setSecretAnswer(secretAnswer);
        user.setSecretQuestion(secretQuestion);
        return user;
    }

}
