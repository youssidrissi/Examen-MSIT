package org.example.mediaserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.mediaserver.dao.entities.CreatorEntity;
import org.example.mediaserver.dao.entities.VideoEntity;
import org.example.mediaserver.dao.repositories.CreatorRepository;
import org.example.mediaserver.dao.repositories.VideoRepository;
import org.example.mediaserver.mappers.CreatorMapper;
import org.example.mediaserver.mappers.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.proto.*;

import java.util.Optional;

@GrpcService
public class VideoService extends VideoServiceGrpc.VideoServiceImplBase {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private CreatorMapper creatorMapper;

    @Override
    public void uploadVideo(UploadVideoRequest request, StreamObserver<Video> responseObserver) {

        CreatorEntity creatorEntity = creatorMapper.fromProtoToEntity(request.getCreator());


        Optional<CreatorEntity> existingCreator = creatorRepository.findByEmail(creatorEntity.getEmail());

        CreatorEntity savedCreator;
        if (existingCreator.isPresent()) {
            savedCreator = existingCreator.get();
        } else {

            savedCreator = creatorRepository.save(creatorEntity);
        }


        VideoEntity videoEntity = videoMapper.fromUploadRequestToEntity(request);
        videoEntity.setCreator(savedCreator);


        VideoEntity savedVideo = videoRepository.save(videoEntity);


        Video video = videoMapper.fromEntityToProto(savedVideo);

        responseObserver.onNext(video);
        responseObserver.onCompleted();
    }

    @Override
    public void getVideo(VideoIdRequest request, StreamObserver<Video> responseObserver) {
        Long videoId = Long.parseLong(request.getId());
        VideoEntity videoEntity = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        Video video = videoMapper.fromEntityToProto(videoEntity);

        responseObserver.onNext(video);
        responseObserver.onCompleted();
    }
}