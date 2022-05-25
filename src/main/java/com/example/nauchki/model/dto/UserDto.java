package com.example.nauchki.model.dto;

import com.example.nauchki.model.Role;
import com.example.nauchki.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String login;
    private String password;
    private String number;
    private String Email;
    private String secretAnswer;
    private Set<Role> roleList;
    private String secretQuestion;
    private String activationCode;
    private List<ChildrenDto> childrens;
    private List<AttachedFileDto> images;
    private Long baseImgId;

    public UserDto(Long id, String name, String login, String password, String number, String email, Set<Role> roleList, List<AttachedFileDto> images, Long baseImgId, List<ChildrenDto> childrens) {
        this.id = id;
        this.username = name;
        this.login = login;
        this.password = password;
        this.number = number;
        this.Email = email;
        this.roleList = roleList;
        this.images = images;
        this.baseImgId = baseImgId;
        this.childrens = childrens;
    }

    public UserDto() {
    }

    public static UserDto valueOf(User user) {
        List<ChildrenDto> childrenDtos = user.getChildrenList().stream().map(ChildrenDto::valueOf).collect(Collectors.toList());
        List<AttachedFileDto> filesDtos = user.getFiles().stream().map(AttachedFileDto::valueOf).collect(Collectors.toList());
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getLogin(),
                user.getPassword(),
                user.getNumber(),
                user.getEmail(),
                user.getGrantedAuthorities(),
                filesDtos,
                user.getBaseImageId(),
                childrenDtos
        );
    }

    public User mapToUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setLogin(login);
        user.setPassword(password);
        user.setNumber(number);
        user.setEmail(Email);
        user.setSecretAnswer(secretAnswer);
        user.setSecretQuestion(secretQuestion);
        user.setBaseImageId(baseImgId);
        return user;
    }

}
