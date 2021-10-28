package com.example.nauchki.service;

import com.example.nauchki.model.Children;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.ChildrenDto;
import com.example.nauchki.repository.ChildrenRepository;
import com.example.nauchki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildrenService {

    private final ChildrenRepository childrenRepository;
    private final UserRepository userRepository;


    public boolean addChildren(Long id, ChildrenDto childrenDto) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() & childrenDto.getName() != null) {
            Children children = childrenDto.mapToChildren();
            children.setParent(user.get());
            user.get().addChildren(children);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }


    public List<ChildrenDto> getChildren(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Children> childrens = user.get().getChildrenList();
            return childrens.stream().map(Children::mapToChildrenDto).collect(Collectors.toList());
        }
        return null;
    }

    public List<ChildrenDto> getChildren(Children children) {
        List<Children> childrenList = childrenRepository.findAll(Example.of(children));
        if (!childrenList.isEmpty()) {
            return childrenList.stream().map(Children::mapToChildrenDto).collect(Collectors.toList());
        }
        return null;
    }

    public boolean editChildren(Children children) {
        Optional<Children> findChildren = childrenRepository.findById(children.getId());
        if (findChildren.isPresent()) {
            if (children.getName() != null){
                findChildren.get().setName(children.getName());}
            if (children.getDateOfBirth() != null){
                findChildren.get().setDateOfBirth(children.getDateOfBirth());}
            if (children.getTimeOfBirth() != null){
                findChildren.get().setTimeOfBirth(children.getTimeOfBirth());}
            childrenRepository.save(findChildren.get());
            return true;
        }
        return false;
    }

    public boolean deleteChildren(Children children){
        Optional<Children> findChildren = childrenRepository.findById(children.getId());
        if(findChildren.isPresent()){
            childrenRepository.delete(findChildren.get());
            return true;
        }
        return false;
    }
}
