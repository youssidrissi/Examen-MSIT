package org.example.mediaclient.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.mediaclient.dto.CreatorDto;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.mapper.CreatorMapper;
import org.example.mediaclient.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.proto.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreatorServiceClient {

    @GrpcClient("mediaserver")
    CreatorServiceGrpc.CreatorServiceBlockingStub stub;

    @Autowired
    private CreatorMapper creatorMapper;

    @Autowired
    private VideoMapper videoMapper;

    public CreatorDto getCreator(String id) {
        CreatorIdRequest request = CreatorIdRequest.newBuilder()
                .setId(id)
                .build();
        Creator creator = stub.getCreator(request);
        return creatorMapper.fromCreatorProtoToCreatorDto(creator);
    }

    public List<VideoDto> getCreatorVideos(String id) {
        CreatorIdRequest request = CreatorIdRequest.newBuilder()
                .setId(id)
                .build();
        VideoStream videoStream = stub.getCreatorVideos(request);
        return videoStream.getVideosList().stream()
                .map(videoMapper::fromVideoProtoToVideoDto)
                .collect(Collectors.toList());
    }
}