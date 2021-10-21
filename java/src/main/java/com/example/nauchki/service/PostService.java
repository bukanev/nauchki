package com.example.nauchki.service;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepo postRepo;

    @Value("src/main/resources/img")
    private String uploadPath;



    public boolean addPost(User user, String text, String tag, MultipartFile file){
        Post post = new Post(text, tag, user);
        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));
                post.setImg_path(resultFilename);
            }
            postRepo.save(post);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Set<PostDto> getPost(String filter) {
        return postRepo.findByTag(filter).stream().map(PostDto::valueOf).collect(Collectors.toSet());
    }

    public Set<PostDto> getAllPost() {
        return postRepo.findAll().stream().map(PostDto::valueOf).collect(Collectors.toSet());
    }
}
