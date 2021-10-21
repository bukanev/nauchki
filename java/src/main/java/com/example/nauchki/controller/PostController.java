package com.example.nauchki.controller;


import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;

    @GetMapping("/post")
    public Set<PostDto> main(@RequestParam(required = false, defaultValue = "") String filter) {
        if (filter != null && !filter.isEmpty()) {
            return postService.getPost(filter);
        }
        return postService.getAllPost();
    }

    @PostMapping(value = "/post")
    public ResponseEntity<HttpStatus> add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file){

        return postService.addPost(user,text, tag, file) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
