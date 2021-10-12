package com.example.nauchki.model;

import com.example.nauchki.model.dto.ChildrenDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    private Integer activate;

    private String secretQuestion;

    private String secretAnswer;


    public User() {}

    public User(Long id,String username) {
        this.id = id;
        this.username = username;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Collection<Role> roles;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Children> childrenList;

    public Collection<Role> getRoles() {
        return roles;
    }
    public void addChildren(Children children) {
        this.childrenList.add(children);
    }
}
