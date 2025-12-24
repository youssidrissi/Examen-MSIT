package org.example.mediaclient.controller;

import org.example.mediaclient.dto.CreatorDto;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.service.CreatorServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creators")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreatorController {

    @Autowired
    private CreatorServiceClient creatorService;

    @GetMapping("/{id}")
    public CreatorDto getCreator(@PathVariable String id) {
        return creatorService.getCreator(id);
    }

    @GetMapping("/{id}/videos")
    public List<VideoDto> getCreatorVideos(@PathVariable String id) {
        return creatorService.getCreatorVideos(id);
    }
}