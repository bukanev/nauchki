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

import java.security.Principal;
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
    public Long add(
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file){

        Post post = new Post(tag,title,subtitle,text);
        return postService.addPost(post , file);
    }

    @PostMapping("/delpost/{id}")
    public ResponseEntity<HttpStatus> getPostService(@PathVariable Long id) {
        return postService.deletePost(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping(value = "/posts/{postid}/image")
    public String addImage(@PathVariable(name="postid") Long postId,
                           @RequestParam(name="description", required = false) String description,
                           @RequestParam(name="tags", required = false) String tags,
                           @RequestParam("file") MultipartFile file,
                           Principal principal){
        return postService.addImage(postId, tags, description, file, principal);
    }

    @DeleteMapping(value = "/posts/{postid}/image/{imgid}")
    public void delImages(@PathVariable(name="postid") Long postId, @PathVariable(name="imgid") Long imgid, @RequestParam("file") MultipartFile file, Principal principal){
        postService.delImage(postId, imgid, principal);
    }

    @DeleteMapping(value = "/posts/{postid}/images")
    public void delImages(@PathVariable(name="postid") Long postId, @RequestParam("file") MultipartFile file, Principal principal){
        postService.delAllImages(postId, file, principal);
    }

}
