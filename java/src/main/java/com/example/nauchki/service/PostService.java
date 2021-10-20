package com.example.nauchki.service;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
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

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepo postRepo;

    @Value("src/main/resources/img")
    private String uploadPath;

    public Set<Post> getPost(String filter) {
        return (Set<Post>) postRepo.findByTag(filter);
    }



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
                post.setFilename(resultFilename);
            }
            postRepo.save(post);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Set<Post> getAllPost() {
        return (Set<Post>) postRepo.findAll();
    }
}
