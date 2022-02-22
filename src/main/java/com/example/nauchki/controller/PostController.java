package com.example.nauchki.controller;


import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.service.PostService;
import com.example.nauchki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

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
            @RequestParam("file") MultipartFile file,
            Principal principal){

        Post post = new Post(tag,title,subtitle,text);
        User author = userService.getUser(principal.getName()).mapToUser();
        post.setAuthor(author);
        return postService.addPost(post , file) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delpost/{id}")
    public ResponseEntity<HttpStatus> getPostService(@PathVariable Long id, Principal principal) {
        return postService.deletePost(id, principal) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
