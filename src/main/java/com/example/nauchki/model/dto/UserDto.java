package com.example.nauchki.model.dto;

import com.example.nauchki.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    private String secretQuestion;
    private String secretAnswer;


    public UserDto(Long id, String name, String login, String password,String number, String mail) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.number = number;
        this.Email = mail;
    }

    public UserDto(String name, String login, String password, String number, String email, String secretQuestion, String secretAnswer) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.number = number;
        Email = email;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
    }

    public static UserDto valueOf(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                user.getNumber(),
                user.getEmail()
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
