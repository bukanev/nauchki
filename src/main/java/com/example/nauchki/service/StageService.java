package com.example.nauchki.service;

import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import com.example.nauchki.repository.StandartStageRepo;
import com.example.nauchki.repository.UserStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StandartStageRepo standartStageRepo;
    private final UserStageRepository userStageRepository;

    public boolean saveStandartStage(StandartStage stage) {
        if (standartStageRepo.findByDays(stage.getDays()) == null) {
            stage.setId(null);
            standartStageRepo.save(stage);
            return true;
        }
        return false;
    }

    public boolean editStandartStage(StandartStage stage) {
        if (standartStageRepo.findByDays(stage.getDays()) != null) {
            StandartStage standartStage = standartStageRepo.findByDays(stage.getDays());
            if (stage.getHeightUSSR() != null){
                standartStage.setHeightUSSR(stage.getHeightUSSR());}
            if (stage.getHeightWHO() != null){
                standartStage.setHeightWHO(stage.getHeightWHO());}
            if (stage.getWeightWHO() != null){
                standartStage.setWeightWHO(stage.getWeightWHO());}
            if (stage.getWeightUSSR() != null){
                standartStage.setWeightUSSR(stage.getWeightUSSR());}
            if (stage.getSkills() != null){
                standartStage.setSkills(stage.getSkills());}
            return true;
        }
        return false;
    }

    public boolean saveUserStage(UserStage userStage) {
        try {
            userStageRepository.save(userStage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
