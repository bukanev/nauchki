package com.example.nauchki.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Schema(description = "Роль пользователя")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID записи", required = true, example = "1")
    private Long id;

    @Schema(description = "Название роли", required = true, example = "USER")
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public String getName(){
        return name;
    }
}
