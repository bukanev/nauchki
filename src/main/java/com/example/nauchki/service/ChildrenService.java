package com.example.nauchki.service;

import com.example.nauchki.exceptions.OtherUserDataExeption;
import com.example.nauchki.jwt.JwtProvider;
import com.example.nauchki.model.Children;
import com.example.nauchki.model.ChildrenImg;
import com.example.nauchki.model.StandartStage;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.ChildrenDto;
import com.example.nauchki.repository.ChildrenImgRepo;
import com.example.nauchki.repository.ChildrenRepository;
import com.example.nauchki.repository.StandartStageRepo;
import com.example.nauchki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildrenService {

    private final FileService fileSaver;
    private final ChildrenRepository childrenRepository;
    private final UserRepository userRepository;
    private final StandartStageRepo stageRepo;
    private final ChildrenImgRepo childrenImgRepo;
    private final JwtProvider jwtProvider;

    public boolean addChildren(Long id, Children children, String token) {
        isCorrectParent(id, token);
        if (children.getDateOfBirth() == null) {
            throw new OtherUserDataExeption("The child must have a date of birth.");
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() & children.getName() != null) {
            children.setParent(user.get());
            user.get().addChildren(children);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }


    public List<ChildrenDto> getChildren(Long id, String token) {
        isCorrectParent(id, token);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Children> childrens = user.get().getChildrens();
            for (int i = 0; i < childrens.size(); i++) {
                Children children = childrens.get(i);
                long days = getDays(children.getDateOfBirth());
                List<StandartStage> stageList = stageRepo.findByDaysAndGender((int) days, children.getGender());
                children.setStandartStages(stageList);
            }
            return childrens.stream().map(ChildrenDto::valueOf).collect(Collectors.toList());
        }
        return null;
    }

    private long getDays(String dateOfBirth) {
        DateFormat dateFormat = new SimpleDateFormat(dateOfBirth);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        long days = 0;
        try {
            long difference = format.parse(format.format(new Date())).getTime() - format.parse(dateFormat.format(new Date())).getTime();
            days = difference / (24 * 60 * 60 * 1000);
            System.out.println(difference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public boolean editChildren(Children children, String token) {
        isCorrectParent(getParentId(children.getId()), token);
        Optional<Children> findChildren = childrenRepository.findById(children.getId());
        if (findChildren.isPresent()) {
            if (children.getName() != null) {
                findChildren.get().setName(children.getName());
            }
            if (children.getDateOfBirth() != null) {
                findChildren.get().setDateOfBirth(children.getDateOfBirth());
            }
            if (children.getTimeOfBirth() != null) {
                findChildren.get().setTimeOfBirth(children.getTimeOfBirth());
            }
            childrenRepository.save(findChildren.get());
            return true;
        }
        return false;
    }

    public boolean deleteChildren(Children children, String token) {
        isCorrectParent(getParentId(children.getId()), token);
        Optional<Children> findChildren = childrenRepository.findById(children.getId());
        if (findChildren.isPresent()) {
            childrenRepository.delete(findChildren.get());
            return true;
        }
        return false;
    }

    public String addChildrenImg(MultipartFile file, Long id) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String path = fileSaver.saveFile(file);
            Optional<Children> children = childrenRepository.findById(id);
            children.ifPresent(value -> {
                value.setImg_path(path);
                value.setImg(file.getOriginalFilename());
                childrenRepository.save(value);
            });
            return path;
        }
        return null;
    }

    public String addChildrenImg(MultipartFile file, Long id, String token, Principal principal) {
        isCorrectParent(getParentId(id), token);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<Children> children = childrenRepository.findById(id);
            if (children.get().getParent().equals(userRepository.findByEmail(principal.getName()).get())) {
                String path = fileSaver.saveFile(file);
                children.ifPresent(value -> {
                    value.setImg_path(path);
                    value.setImg(file.getOriginalFilename());
                    childrenRepository.save(value);
                });
                return path;
            }
        }
        return null;
    }

    public String addChildrenImgToList(MultipartFile file, Long id, String comment, String token, Principal principal) {
        isCorrectParent(getParentId(id), token);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<Children> children = childrenRepository.findById(id);
            if (children.get().getParent().equals(userRepository.findByEmail(principal.getName()).get())) {
                String path = fileSaver.saveFile(file);
                children.ifPresent(value -> {
                    ChildrenImg childrenImg = new ChildrenImg();
                    childrenImg.setImgPath(path);
                    childrenImg.setImg(file.getOriginalFilename());
                    childrenImg.setComment(comment);
                    value.addChildrenImg(childrenImg);
                    childrenImgRepo.save(childrenImg);
                    childrenRepository.save(value);
                });
                return path;
            }
        }
        return null;
    }

    public Long getParentId (Long id) {
        return childrenRepository.getById(id).getParent().getId();
    }

    public void isCorrectParent(Long id, String token) {
        String userAuthEmail = jwtProvider.getUsername(token.split(" ")[1]);
        String userGetEmail = userRepository.getById(id).getEmail();
        if (!StringUtils.equals(userAuthEmail, userGetEmail)) {
            throw new OtherUserDataExeption("?????? ???? ?????? ??????????????!");
        }
    }

}
