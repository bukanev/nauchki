package com.example.nauchki.mapper;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public PostDto toDto(Post model){
        PostDto postDto = modelMapper.map(model, PostDto.class);
        postDto.setAuthorId(model.getAuthor().getId());
        postDto.setAuthorName(model.getAuthor().getUsername());
        return postDto;
    }

    public Post toModel(PostDto dto){
        Post post = modelMapper.map(dto, Post.class);
        post.setAuthor(userService.getUserEntity(dto.getAuthorId()));
        return post;
    }

}
