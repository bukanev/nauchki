package com.example.nauchki.controller;


import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/posts")
    public List<PostDto> main(@RequestBody Post post) {
        if (post != null || !post.getTag().isEmpty()) {
            List<PostDto> postDtos = postService.getPost(post);
            return postDtos;
        }
        return postService.getAllPost();
    }

    @GetMapping({"/posts/{tag}" , "/posts"})
    public List<PostDto> getPosts(@PathVariable(required = false) String tag) {
        if(tag != null){
            return postService.getAllPost(tag);
        }
        return postService.getAllPost();
    }

    @GetMapping("/tags")
    public List<String> getTags(){
        return postService.getAllTags();
    }


    @PostMapping(value = "/post")
    public ResponseEntity<HttpStatus> add(
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file){

        Post post = new Post(tag,title,subtitle,text);
        return postService.addPost(post , file) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delpost/{id}")
    public ResponseEntity<HttpStatus> getPostService(@PathVariable Long id) {
        return postService.deletePost(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
