package com.example.nauchki.service;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import com.example.nauchki.repository.ChildrenRepository;
import com.example.nauchki.repository.StandartStageRepo;
import com.example.nauchki.repository.UserStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StandartStageRepo standartStageRepo;
    private final UserStageRepository userStageRepository;
    private final ChildrenRepository childrenRepository;

    public boolean saveStandartStage(StandartStage stage, Principal principal) {
        if (standartStageRepo.findByDaysAndGender(stage.getDays(), stage.getGender()) == null
                & principal.getName().equalsIgnoreCase("admin")) {
            stage.setId(null);
            standartStageRepo.save(stage);
            return true;
        }
        return false;
    }

    public boolean editStandartStage(StandartStage stage, Principal principal) {
        if (standartStageRepo.findByDaysAndGender(stage.getDays(), stage.getGender()) != null
                & principal.getName().equalsIgnoreCase("admin")) {
            StandartStage standartStage = standartStageRepo.findByDaysAndGender(stage.getDays(), stage.getGender());
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

    public boolean saveUserStage(Long id,UserStage userStage) {
        Optional<Children> children = childrenRepository.findById(id);
        if(children.isPresent()) {
            //userStageRepository.save(userStage);
            System.out.println(userStage.toString());
            children.get().addUserStage(userStage);
            childrenRepository.save(children.get());
            return true;
        }
        return false;
    }
}
