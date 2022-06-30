package com.example.nauchki.service;

import com.example.nauchki.exceptions.ResourceNotFoundException;
import com.example.nauchki.jwt.TokenUtils;
import com.example.nauchki.mapper.PostMapper;
import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final FileService fileService;
    private final PostMapper postMapper;
    private final TokenUtils tokenUtils;

    public List<PostDto> getPost(Post filter) {
        return postRepo.findAll(Example.of(filter)).stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    public List<PostDto> getAllPost() {
        return postRepo.findAll().stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    public List<PostDto> getAllPost(String tag) {
        return postRepo.findByTag(tag).stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    //статью может удалить либо автор, либо пользователь с ролью SUPERADMIN
    @Transactional
    public boolean deletePost(Long id, Principal principal) {

        Post post= postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Статья с id '" + id + "' не найдена"));
        boolean permition = hasPermitionForDelete(post, principal.getName());
        if(!permition){
            throw new AccessDeniedException("Удалить статью может только автор, или администратор");
        }

        delAllImages(id, principal);
        postRepo.delete(post);
        return true;
    }

    public Long addPost(Post post, MultipartFile file){
     //   post = postRepo.save(post);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            fileService.saveAttachedFilePost(file, post);
        }
        return post.getId();
    }

    public List<String> getAllTags() {
        return postRepo.findAllTag();
    }

    @Transactional
    public String addImage(Long postId, String tags, String description, MultipartFile file, Principal principal) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<Post> post = postRepo.findById(postId);
            Post postModel = post.orElseThrow(()->new ResourceNotFoundException("Post '" + postId + "' not found"));
            boolean permition = hasPermitionForDelete(postModel, principal.getName());
            if(!permition){
                throw new AccessDeniedException("Загрузить изображение может только автор, или администратор");
            }
            String path = fileService.saveAttachedFile(file, postModel, tags, description);
            postRepo.save(postModel);
            return path;
        }
        return null;
    }

    @Transactional
    public String addImage(Long postId, MultipartFile file, Principal principal) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            Optional<Post> post = postRepo.findById(postId);
            Post postModel = post.orElseThrow(()->new ResourceNotFoundException("Post '" + postId + "' not found"));
            boolean permition = hasPermitionForDelete(postModel, principal.getName());
            if(!permition){
                throw new AccessDeniedException("Загрузить изображение может только автор, или администратор");
            }
            String path = fileService.saveAttachedFile(file, postModel);
            postRepo.save(postModel);
            return path;
        }
        return null;
    }

    @Transactional
    public void delImage(Long postId, Long imgid, Principal principal) {
        Optional<Post> post = postRepo.findById(postId);
        Post postModel = post.orElseThrow(()->new ResourceNotFoundException("Post '" + postId + "' not found"));
        boolean permition = hasPermitionForDelete(postModel, principal.getName());
        if(!permition){
            throw new AccessDeniedException("Удалить изображение может только автор, или администратор");
        }

        boolean fileConsists = postModel.getFiles().stream()
                .anyMatch(v-> v.getId().equals(imgid));
        if(!fileConsists){
            throw new ResourceNotFoundException("File with id '" + imgid + "' not belong to post '" + principal.getName() + "'");
        }
        fileService.deleteAttachedFile(imgid, postModel);
        postRepo.save(postModel);
    }

    @Transactional
    public void delAllImages(Long postId, Principal principal) {
        Optional<Post> post = postRepo.findById(postId);
        Post postModel = post.orElseThrow(()->new ResourceNotFoundException("Post '" + postId + "' not found"));
        boolean permition = hasPermitionForDelete(postModel, principal.getName());
        if(!permition){
            throw new AccessDeniedException("Удалить изображения может только автор, или администратор");
        }
        if(fileService.deleteAllAttachedFiles(postModel)){
            postRepo.save(postModel);
        }
    }

    private boolean hasPermitionForDelete(Post post, String userName){
        boolean permition = false;
        List<String> roles = tokenUtils.getRoles();
        if(roles.contains("SUPERADMIN")){
            permition = true;
        }else if(post.getAuthor().getEmail().equals(userName)){
            permition = true;
        }
        return permition;
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
