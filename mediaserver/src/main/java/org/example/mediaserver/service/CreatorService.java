package org.example.mediaserver.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.mediaserver.dao.entities.CreatorEntity;
import org.example.mediaserver.dao.entities.VideoEntity;
import org.example.mediaserver.dao.repositories.CreatorRepository;
import org.example.mediaserver.mappers.CreatorMapper;
import org.example.mediaserver.mappers.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.proto.*;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class CreatorService extends CreatorServiceGrpc.CreatorServiceImplBase {

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private CreatorMapper creatorMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public void getCreator(CreatorIdRequest request, StreamObserver<Creator> responseObserver) {
        Long creatorId = Long.parseLong(request.getId());
        CreatorEntity creatorEntity = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Creator creator = creatorMapper.fromEntityToProto(creatorEntity);

        responseObserver.onNext(creator);
        responseObserver.onCompleted();
    }

    @Override
    public void getCreatorVideos(CreatorIdRequest request, StreamObserver<VideoStream> responseObserver) {
        Long creatorId = Long.parseLong(request.getId());
        List<VideoEntity> videos = creatorRepository.findVideosByCreatorId(creatorId);

        List<Video> videoList = videos.stream()
                .map(videoMapper::fromEntityToProto)
                .collect(Collectors.toList());

        VideoStream videoStream = VideoStream.newBuilder()
                .addAllVideos(videoList)
                .build();

        responseObserver.onNext(videoStream);
        responseObserver.onCompleted();
    }
}