package com.example.nauchki.mapper;

import com.example.nauchki.model.FileStorage;
import com.example.nauchki.model.dto.FilePathDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={ImgMapper.class})
public interface ImgMapper {

    FilePathDto toDto(FileStorage model);

}
