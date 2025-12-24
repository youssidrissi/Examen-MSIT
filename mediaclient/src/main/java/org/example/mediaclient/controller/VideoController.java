package org.example.mediaclient.controller;

import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.service.VideoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.proto.Creator;
import org.example.proto.UploadVideoRequest;



@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VideoController {

        @Autowired
        private VideoServiceClient videoService;

        

        @PostMapping("/upload")
        public VideoDto uploadVideo(@RequestBody(required = false) VideoDto requestDto) {

                String title = (requestDto != null && requestDto.getTitle() != null) ? requestDto.getTitle()
                                : "Tutorial Video";
                String description = (requestDto != null && requestDto.getDescription() != null)
                                ? requestDto.getDescription()
                                : "Professional video content for learning and development";
                String url = (requestDto != null && requestDto.getUrl() != null) ? requestDto.getUrl()
                                : "https://media-platform.example.com/videos";
                int duration = (requestDto != null && requestDto.getDurationSeconds() > 0)
                                ? requestDto.getDurationSeconds()
                                : 300;


                String creatorName = "Platform Admin";
                String creatorEmail = "admin@mediaplatform.com";

                if (requestDto != null && requestDto.getCreator() != null) {
                        if (requestDto.getCreator().getName() != null) {
                                creatorName = requestDto.getCreator().getName();
                        }
                        if (requestDto.getCreator().getEmail() != null) {
                                creatorEmail = requestDto.getCreator().getEmail();
                        }
                }


                Creator creator = Creator.newBuilder()
                                .setName(creatorName)
                                .setEmail(creatorEmail)
                                .setId("1")
                                .build();


                UploadVideoRequest request = UploadVideoRequest.newBuilder()
                                .setTitle(title)
                                .setDescription(description)
                                .setUrl(url)
                                .setDurationSeconds(duration)
                                .setCreator(creator)
                                .build();

                VideoDto videoDto = videoService.uploadVideo(request);
                System.out.println("Video uploaded successfully: " + videoDto);
                return videoDto;
        }

        @GetMapping("/{id}")
        public VideoDto getVideo(@PathVariable String id) {
                return videoService.getVideo(id);
        }
}