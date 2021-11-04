package com.example.nauchki.service;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepo postRepo;

    @Value("${upload.path}")
    private String uploadPath;

    private String localDir = System.getProperty("user.dir");

    public List<PostDto> getPost(Post filter) {
        return postRepo.findAll(Example.of(filter)).stream().map(PostDto::valueOf).collect(Collectors.toList());
    }

    public List<PostDto> getAllPost() {
        return postRepo.findAll().stream().map(PostDto::valueOf).collect(Collectors.toList());
    }

    public List<PostDto> getAllPost(String tag) {
        return postRepo.findByTag(tag).stream().map(PostDto::valueOf).collect(Collectors.toList());
    }

    public boolean deletePost(Long id) {
        try {
            Optional<Post> post = postRepo.findById(id);
            if(post.isPresent()){
                File file = new File(localDir + uploadPath + post.get().getImg_path());
                file.delete();
            }
            postRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPost(Post post, MultipartFile file){
        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(localDir + uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                File img = new File(localDir + uploadPath + "/" , resultFilename);
                file.transferTo(img);
                post.setImg_path("https://nauchki.herokuapp.com/img/" + resultFilename);
            }
            postRepo.save(post);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllTags() {
        return postRepo.findAllTag();
    }

    /*public boolean addPost(User user, String text, String tag, MultipartFile file){
        Post post = new Post(text, tag, user);
        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(localDir + uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                File img = new File(localDir + uploadPath + "/" , resultFilename);
                file.transferTo(img);
                post.setImg_path("https://nauchki.herokuapp.com/img/" + resultFilename);
            }
            postRepo.save(post);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
