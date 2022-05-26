package com.example.nauchki.mapper;

import com.example.nauchki.model.Post;
import com.example.nauchki.model.User;
import com.example.nauchki.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto toDto(User model){
        return modelMapper.map(model, UserDto.class);
    }

    public User toModel(UserDto dto){
        return modelMapper.map(dto, User.class);
    }

}
