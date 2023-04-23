package com.example.microgram.controller;

import com.example.microgram.dto.PublicationDto;
import com.example.microgram.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationsService;

    @GetMapping("/takePublicationForUser/{userId}")
    public ResponseEntity<List<PublicationDto>>  takePublicationsForUser(@PathVariable String userId){
        return new ResponseEntity<>(publicationsService.getPublicationForUser(Integer.parseInt(userId)), HttpStatus.OK);
    }
    @GetMapping("/takePublicationsForUserBySubscriptions/{userId}")
    public ResponseEntity<List<PublicationDto>> takePublicationsForUserBySubscriptions(@PathVariable String userId){
        return new ResponseEntity<>(publicationsService.getPublicationsForUserBySubscriptions(Integer.parseInt(userId)), HttpStatus.OK);
    }
    @PostMapping("/{image}/{description}")
    public PublicationDto addPublication(@PathVariable String image, @PathVariable String description, Authentication authentication){
        return publicationsService.addPublication(image, description, authentication);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        if (publicationsService.deletePublication(id))
            return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();
    }
}
