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
        try {
            standartStageRepo.save(stage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveUserStage(UserStage userStage){
        try {
            userStageRepository.save(userStage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
