package org.example.mediaserver.mappers;

import org.example.mediaserver.dao.entities.CreatorEntity;
import org.springframework.stereotype.Component;
import org.example.proto.Creator;

@Component
public class CreatorMapper {

    public CreatorEntity fromProtoToEntity(Creator creator) {
        CreatorEntity entity = new CreatorEntity();

        entity.setName(creator.getName());
        entity.setEmail(creator.getEmail());
        return entity;
    }

    public Creator fromEntityToProto(CreatorEntity entity) {
        return Creator.newBuilder()
                .setId(String.valueOf(entity.getId()))
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .build();
    }
}