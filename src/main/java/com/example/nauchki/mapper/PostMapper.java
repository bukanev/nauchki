package com.example.nauchki.mapper;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostDto toDto(Post model){
        return modelMapper.map(model, PostDto.class);
    }

    public Post toModel(PostDto dto){
        return modelMapper.map(dto, Post.class);
    }

}
