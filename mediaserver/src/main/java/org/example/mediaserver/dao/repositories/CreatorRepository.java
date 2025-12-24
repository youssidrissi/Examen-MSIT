package org.example.mediaserver.dao.repositories;

import org.example.mediaserver.dao.entities.CreatorEntity;
import org.example.mediaserver.dao.entities.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreatorRepository extends JpaRepository<CreatorEntity, Long> {


    Optional<CreatorEntity> findByEmail(String email);


    @Query("SELECT v FROM VideoEntity v WHERE v.creator.id = :creatorId")
    List<VideoEntity> findVideosByCreatorId(@Param("creatorId") Long creatorId);
}