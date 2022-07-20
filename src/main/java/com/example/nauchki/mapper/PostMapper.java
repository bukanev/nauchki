package com.example.nauchki.mapper;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.dto.PostDto;
import com.example.nauchki.model.dto.PostSaveDto;
import com.example.nauchki.model.dto.PostTitle;
import com.example.nauchki.service.UserService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={UserService.class})
public interface PostMapper {

    Post toModel(PostDto postDto);

    PostDto toDto(Post model);

    PostTitle toTitleDto(Post model);

    Post moveChange(Post newPostData);

    Post toModel(PostSaveDto postDto);

}
