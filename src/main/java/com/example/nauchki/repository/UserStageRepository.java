package com.example.nauchki.repository;

import com.example.nauchki.model.UserStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStageRepository extends JpaRepository<UserStage, Long> {
}
