package org.example.mediaserver.dao.repositories;

import org.example.mediaserver.dao.entities.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {
}