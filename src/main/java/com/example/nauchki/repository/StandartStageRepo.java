package com.example.nauchki.repository;

import com.example.nauchki.model.StandartStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandartStageRepo extends JpaRepository<StandartStage, Long> {
    @Query(value = "select * from standart_stage where days <= :day and gender LIKE :gender order by days", nativeQuery = true)
    List<StandartStage> findByDaysAndGender(@Param("day") Integer days,@Param("gender") String gender);

    @Query(value = "select * from Standart_stage where days <=" + ":day order by days", nativeQuery = true)
    List<StandartStage> findByDay(@Param("day") int day);
}
