package com.example.nauchki.mapper;

import com.example.nauchki.model.FileStorage;
import com.example.nauchki.model.dto.AttachedFileDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapper {

    private final ModelMapper modelMapper;

    public AttachedFileDto toDto(FileStorage model){
        return modelMapper.map(model, AttachedFileDto.class);
    }

    public FileStorage toModel(AttachedFileDto dto){
        return modelMapper.map(dto, FileStorage.class);
    }

}
