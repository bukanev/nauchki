package com.example.nauchki.repository;

import com.example.nauchki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    Optional<User> findById(Long id);
    User findByUsernameAndLogin(String username,String login);
}
