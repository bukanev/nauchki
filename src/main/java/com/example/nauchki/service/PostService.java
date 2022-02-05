package com.example.nauchki.service;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final FileSaver saverFile;

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
        //TODO Сделать удаление.
        return false;
    }

    public boolean addPost(Post post, MultipartFile file){
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String path = saverFile.saveFile(file);
            postRepo.save(post);
            post.setImg_path(path);
            return true;
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
