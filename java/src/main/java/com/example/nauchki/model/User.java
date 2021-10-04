package com.example.nauchki.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String login;

    private String password;

    private String email;

    private String number;

    private Integer score;


    public User() {}

    public User(Long id,String username) {
        this.id = id;
        this.username = username;
    }

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Collection<Role> roles;


    public Collection<Role> getRoles() {
        return roles;
    }

}
