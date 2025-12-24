package org.example.mediaclient.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.proto.*;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VideoServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceClient.class);

    @GrpcClient("mediaserver")
    public VideoServiceGrpc.VideoServiceBlockingStub stub;

    @Autowired
    private VideoMapper mapper;

    public VideoDto uploadVideo(UploadVideoRequest videoRequest) {
        try {
            logger.info("Uploading video via gRPC to mediaserver");
            Video video = stub.uploadVideo(videoRequest);
            logger.info("Video uploaded successfully: {}", video.getId());
            return mapper.fromVideoProtoToVideoDto(video);
        } catch (StatusRuntimeException e) {
            logger.error("gRPC Error uploading video: {}", e.getStatus(), e);
            throw new RuntimeException("Failed to upload video: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error uploading video", e);
            throw new RuntimeException("Failed to upload video", e);
        }
    }

    public VideoDto getVideo(String id) {
        try {
            logger.info("Getting video {} via gRPC from mediaserver", id);
            VideoIdRequest request = VideoIdRequest.newBuilder()
                    .setId(id)
                    .build();
            Video video = stub.getVideo(request);
            logger.info("Video retrieved successfully: {}", video.getId());
            return mapper.fromVideoProtoToVideoDto(video);
        } catch (StatusRuntimeException e) {
            logger.error("gRPC Error getting video {}: {}", id, e.getStatus(), e);
            throw new RuntimeException("Failed to get video: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error getting video {}", id, e);
            throw new RuntimeException("Failed to get video", e);
        }
    }
}