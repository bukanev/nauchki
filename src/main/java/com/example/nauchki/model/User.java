package com.example.nauchki.model;

import com.example.nauchki.utils.FileContainer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Schema(description = "Модель пользователя")
public class User implements UserDetails, FileContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID пользователя", required = true, example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Василий")
    private String username;

    @Schema(description = "Логин", example = "Vasya")
    private String login;

    @Schema(description = "Пароль", example = "Gfk33!")
    private String password;

    @Schema(description = "Электронная почта", example = "vasya@mail.ru")
    private String email;

    @Schema(description = "Номер телефона", example = "89187454514")
    private String number;

    @Schema(description = "Признак активности подписки (0 - некативна, 1 - активна)", example = "1")
    private Integer activate; //тут будет активная или не активная подписка.

    @Schema(description = "Секретный вопрос для восстановления пароля", example = "Любимое число")
    private String secretQuestion;

    @Schema(description = "Ответ на секретный ворос для восстановления пароля", example = "643345864")
    private String secretAnswer;

       private String activationCode;

    private Integer active; //2 - означает почта подтверждена.
    private Long baseImageId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "attached_files_user_images",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name="file_id", referencedColumnName = "id"))
    private List<FileStorage> images;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> grantedAuthorities;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Children> childrens;

    private Integer resetPasswordCode;

    public User(String username, String password, Collection<? extends GrantedAuthority> grantedAuthorities) {
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = roles;
    }

    public void addChildren(Children children) {
        this.childrens.add(children);
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", activate=" + activate +
                ", secretQuestion='" + secretQuestion + '\'' +
                ", secretAnswer='" + secretAnswer + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    } //пока заглушка

    public Set<Role> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getEntityType() {
        return "user_images";
    }
    @Override
    public Long getEntityId() {
        return this.id;
    }

    @Override
    public List<FileStorage> getFiles() {
        if(this.images==null){
            this.images = new ArrayList<>();
        }
        return this.images;
    }

    @Override
    public void setFiles(List<FileStorage> images) {
        this.images = images;
    }

}
