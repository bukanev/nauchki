package com.example.nauchki.controller;


import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @ApiOperation("Получение всех статей")
    @PostMapping("/posts")
    public List<PostDto> main(@RequestBody Post post) {
        if (post != null && !post.getTag().isEmpty()) {
            List<PostDto> postDtos = postService.getPost(post);
            return postDtos;
        }
        return postService.getAllPost();
    }

    @ApiOperation("Получение всех статей, соответствующих определенному тэгу")
    @GetMapping({"/posts/{tag}" , "/posts"})
    public List<PostDto> getPosts(
            @PathVariable(required = false) @Parameter(description = "Тэг статьи") String tag) {
        if(tag != null){
            return postService.getAllPost(tag);
        }
        return postService.getAllPost();
    }

    @ApiOperation("Получение всех тэгов")
    @GetMapping("/tags")
    public List<String> getTags(){
        return postService.getAllTags();
    }


    @ApiOperation("Добавление статьи")
    @PostMapping(value = "/post")
    public Long add(
            @RequestParam @Parameter(description = "Название статьи", required = true) String title,
            @RequestParam @Parameter(description = "Дополнение к названию статьи") String subtitle,
            @RequestParam @Parameter(description = "Текст статьи", required = true) String text,
            @RequestParam @Parameter(description = "Тэги статьи") String tag,
            @RequestParam("file") MultipartFile file){

        Post post = new Post(tag,title,subtitle,text);
        return postService.addPost(post , file);
    }

    @ApiOperation("Удаление статьи по id")
    @DeleteMapping("/delpost/{id}")
    public ResponseEntity<HttpStatus> getPostService(
            @PathVariable @Parameter(description = "Идентификатор статьи", required = true) Long id, Principal principal) {
        return postService.deletePost(id, principal) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @ApiOperation("Добавление изображения в статью по id статьи")
    @PostMapping(value = "/posts/{postid}/image")
    public String addImage(@PathVariable(name="postid") @Parameter(description = "Идентификатор статьи", required = true) Long postId,
                           @RequestParam(name="description", required = false) @Parameter(description = "Описание изображения") String description,
                           @RequestParam(name="tags", required = false) @Parameter(description = "Тэги изображения") String tags,
                           @RequestParam("file") MultipartFile file,
                           Principal principal){
        return postService.addImage(postId, tags, description, file, principal);
    }

    @ApiOperation("Удаление из статьи с определенным id изображения с указанным id")
    @DeleteMapping(value = "/posts/{postid}/image/{imgid}")
    public void delImages(
            @PathVariable(name="postid") @Parameter(description = "Идентификатор статьи", required = true) Long postId,
            @PathVariable(name="imgid") @Parameter(description = "Идентификатор изображения", required = true) Long imgid,
            Principal principal){
        postService.delImage(postId, imgid, principal);
    }

}
