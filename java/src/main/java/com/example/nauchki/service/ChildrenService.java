package com.example.nauchki.service;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.ChildrenDto;
import com.example.nauchki.repository.ChildrenRepository;
import com.example.nauchki.repository.StandartStageRepo;
import com.example.nauchki.repository.UserRepository;
import com.example.nauchki.repository.UserStageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildrenService {

    private final ChildrenRepository childrenRepository;
    private final StandartStageRepo standartStageRepo;
    private final UserStageRepository userStageRepository;
    private final UserRepository userRepository;


    public boolean addChildren(Long id, ChildrenDto childrenDto){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            Children children = childrenDto.mapToChildren();
            children.setParent(user.get());
            user.get().addChildren(children);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }


    public List<ChildrenDto> getChildren(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            //List<Children> childrens = childrenRepository.findByParent(user.get());
            List<Children> childrens = childrenRepository.findAllByParentId(id);
            return childrens.stream().map(Children::mapToChildrenDto).collect(Collectors.toList());
        }
        return null;
    }

}
