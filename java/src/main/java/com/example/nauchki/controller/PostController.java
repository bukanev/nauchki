package com.example.nauchki.controller;


import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/posts")
    public Set<PostDto> main(@RequestBody Post post) {
        if (post != null || !post.getTag().isEmpty()) {
            Set<PostDto> postDtos = postService.getPost(post);
            return postDtos;
        }
        return postService.getAllPost();
    }


    @PostMapping(value = "/post")
    public ResponseEntity<HttpStatus> add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file, Principal principal){
        if(principal != null){
            System.out.println("============ "+principal.toString());
        }
        return postService.addPost(user,text, tag, file) ?
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
