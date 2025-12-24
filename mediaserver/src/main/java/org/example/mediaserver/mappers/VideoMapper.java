package org.example.mediaserver.mappers;

import org.example.mediaserver.dao.entities.VideoEntity;
import org.springframework.stereotype.Component;
import org.example.proto.Video;
import org.example.proto.UploadVideoRequest;

@Component
public class VideoMapper {

    public VideoEntity fromUploadRequestToEntity(UploadVideoRequest request) {
        VideoEntity entity = new VideoEntity();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setUrl(request.getUrl());
        entity.setDurationSeconds(request.getDurationSeconds());

        return entity;
    }

    public Video fromEntityToProto(VideoEntity entity) {
        return Video.newBuilder()
                .setId(String.valueOf(entity.getId()))
                .setTitle(entity.getTitle())
                .setDescription(entity.getDescription())
                .setUrl(entity.getUrl())
                .setDurationSeconds(entity.getDurationSeconds())
                .setCreator(org.example.proto.Creator.newBuilder()
                        .setId(String.valueOf(entity.getCreator().getId()))
                        .setName(entity.getCreator().getName())
                        .setEmail(entity.getCreator().getEmail())
                        .build())
                .build();
    }
}