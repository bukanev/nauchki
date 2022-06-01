package com.example.nauchki.mapper;

import com.example.nauchki.model.FileComment;
import com.example.nauchki.model.dto.FileCommentDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileCommentMapper {

    private final ModelMapper modelMapper;

    public FileCommentDto toDto(FileComment model){
        return modelMapper.map(model, FileCommentDto.class);
    }

    public FileComment toModel(FileCommentDto dto){
        return modelMapper.map(dto, FileComment.class);
    }

}
