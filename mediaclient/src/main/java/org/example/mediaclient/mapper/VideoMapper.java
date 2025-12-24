package org.example.mediaclient.mapper;

import org.example.mediaclient.dto.VideoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.example.proto.Video;

@Component
public class VideoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public VideoDto fromVideoProtoToVideoDto(Video video) {
        return modelMapper.map(video, VideoDto.class);
    }
}