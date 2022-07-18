package com.example.nauchki.controller;


import com.example.nauchki.exceptions.DeniedException;
import com.example.nauchki.jwt.TokenUtils;
import com.example.nauchki.mapper.PostMapper;
import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.model.dto.PostSaveDto;
import com.example.nauchki.model.dto.PostTitle;
import com.example.nauchki.service.PostService;
import com.example.nauchki.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostService postService;
    private final UserService userService;
    private final TokenUtils tokenUtils;
    private final PostMapper postMapper;


    @Value("${upload.path}")
    private String uploadPath;

    @ApiOperation("Получение всех статей")
    @PostMapping("/posts")
    public List<PostDto> main(@RequestBody Post post) {
        if (post != null && !post.getTag().isEmpty()) {
            return postService.getPost(post);
        }
        return postService.getAllPost();
    }

    @ApiOperation("Получение всех статей по страницам")
    @PostMapping("/posts/page/{pagenumber}/{pagesize}")
    public Page<PostTitle> getPage(@RequestBody(required = false) Post post,
                                   @PathVariable(name = "pagenumber") @Parameter(description = "Номер страницы") int pageNumber,
                                   @PathVariable(name = "pagesize") @Parameter(description = "Размер страницы") int pageSize) {
        if (post != null && !post.getTag().isEmpty()) {
            return postService.getPost(post, pageNumber, pageSize);
        }
        return postService.getAllPost(pageNumber, pageSize);
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

    @ApiOperation("Получение страницы со статьями, соответствующих определенному тэгу")
    @GetMapping({"/posts/{tag}/page/{pagenumber}/{pagesize}" , "/posts"})
    public Page<PostTitle> getPageByTags(
            @PathVariable(required = false) @Parameter(description = "Тэг статьи") String tag,
            @PathVariable(name = "pagenumber") @Parameter(description = "Номер страницы") int pageNumber,
            @PathVariable(name = "pagesize") @Parameter(description = "Размер страницы") int pageSize) {
        if(tag != null){
            return postService.getAllPost(tag, pageNumber, pageSize);
        }
        return postService.getAllPost(pageNumber, pageSize);
    }

    @ApiOperation("Получение всех тэгов")
    @GetMapping("/tags")
    public List<String> getTags(){
        return postService.getAllTags();
    }


    @ApiOperation("Добавление статьи")
    @PostMapping(value = "/post")
    public PostDto add(
            @RequestParam @Parameter(description = "Название статьи", required = true) String title,
            @RequestParam @Parameter(description = "Дополнение к названию статьи") String subtitle,
            @RequestParam @Parameter(description = "Текст статьи", required = true) String text,
            @RequestParam @Parameter(description = "Тэги статьи") String tag,
            @RequestParam("file") MultipartFile file){

        String userName = tokenUtils.getPrincipalName().orElseThrow(()-> new DeniedException("Добавление статей доступно только авторизованным пользователям"));
        User user = userService.getUserEntity(userName);
        Post post = Post.builder()
                .tag(tag)
                .title(title)
                .subtitle(subtitle)
                .text(text)
                .author(user).build();
        return postService.addPost(post, file);
    }

    @ApiOperation(
            value = "Изменение статьи",
            notes = "Изменение статьи (для редактирования изображений нужно использовать методы delete:/posts/{postid}/image/{imgid} и post:/posts/{postid}/image )")
    @PutMapping(value = "/post/{postid}")
    public PostDto update(
            @PathVariable(name="postid") @Parameter(description = "Идентификатор статьи", required = true) Long postId,
            @RequestBody PostSaveDto postDto){
        Post post = postMapper.toModel(postDto);
        return postService.savePost(postId, post);
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
    @PreAuthorize("hasRole('ADMIN') || hasRole('AUTHOR')")
    public void delImages(
            @PathVariable(name="postid") @Parameter(description = "Идентификатор статьи", required = true) Long postId,
            @PathVariable(name="imgid") @Parameter(description = "Идентификатор изображения", required = true) Long imgid,
            Principal principal){
        postService.delImage(postId, imgid, principal);
    }

}
