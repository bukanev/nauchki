package com.example.nauchki.repository;

import com.example.nauchki.model.StandartStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandartStageRepo extends JpaRepository<StandartStage, Long> {
    StandartStage findByDaysAndGender(Integer days, String gender);
}
