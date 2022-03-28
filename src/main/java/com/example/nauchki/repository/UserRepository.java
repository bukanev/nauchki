package com.example.nauchki.repository;

import com.example.nauchki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    Optional<User> findByUsernameAndLogin(String username,String login);
    User findByUsername(String name);
    User findByActivationCode(String code);
    Optional<User> findByUsernameAndPassword(String login, String password);
    Optional<User> findByEmail(String email);
}
