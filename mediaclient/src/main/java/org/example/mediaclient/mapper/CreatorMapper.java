package org.example.mediaclient.mapper;

import org.example.mediaclient.dto.CreatorDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.example.proto.Creator;

@Component
public class CreatorMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public CreatorDto fromCreatorProtoToCreatorDto(Creator creator) {
        return modelMapper.map(creator, CreatorDto.class);
    }
}