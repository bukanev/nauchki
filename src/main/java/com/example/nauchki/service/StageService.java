package com.example.nauchki.service;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.UserStage;
import com.example.nauchki.repository.ChildrenRepository;
import com.example.nauchki.repository.StandartStageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StandartStageRepo standartStageRepo;
    private final ChildrenRepository childrenRepository;

    public boolean saveStandartStage(StandartStage stage, String name) {
        if (standartStageRepo.findByDaysAndGender(stage.getDays(), stage.getGender()) == null
                & name.equalsIgnoreCase("admin")) {
            stage.setId(null);
            standartStageRepo.save(stage);
            return true;
        }
        return false;
    }

    public boolean editStandartStage(StandartStage stage, String name) {
        if (standartStageRepo.findByDaysAndGender(stage.getDays(), stage.getGender()) != null
                & name.equalsIgnoreCase("admin")) {
            Optional<StandartStage> standartStage = standartStageRepo.findById(stage.getId());
            if (standartStage.isPresent()){
                standartStageRepo.save(stage);
                return true;
            }
        }
        return false;
    }
    public boolean editStandartStage(StandartStage stage) {
        if (stage.getId() != null) {
            Optional<StandartStage> standartStage = standartStageRepo.findById(stage.getId());
            if (standartStage.isPresent()){
                standartStageRepo.save(stage);
                return true;
            }
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

    public List<StandartStage> getAllStandartStage() {
        return standartStageRepo.findAll(Sort.by("id"));
    }

    public StandartStage getStage(Long id) {
        return standartStageRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
