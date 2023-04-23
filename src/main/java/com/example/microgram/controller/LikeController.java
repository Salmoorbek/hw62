package com.example.microgram.controller;

import com.example.microgram.dto.LikeDto;
import com.example.microgram.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/checkPublicationLikes/{publicationId}")
    public ResponseEntity<String> checkPublicationLike(@PathVariable String publicationId) {
        return new ResponseEntity<>(likeService.isUserLikedPublications(Integer.parseInt(publicationId)), HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public LikeDto addLike(@RequestBody LikeDto likeDto) {
        return likeService.likePublication(likeDto.getUserId(), likeDto.getLikedPublicationId());
    }
}
