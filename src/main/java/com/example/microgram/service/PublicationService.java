package com.example.microgram.service;

import com.example.microgram.dao.PublicationDao;
import com.example.microgram.dto.PublicationDto;
import com.example.microgram.entity.Publication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationDao publicationDao;

    public List<PublicationDto> getPublicationForUser(int userId){
        return publicationDao.getPublicationsForUser(userId)
                .stream()
                .map(PublicationDto::from)
                .collect(Collectors.toList());
    }

    public List<PublicationDto> getPublicationsForUserBySubscriptions(int userId) {
        return publicationDao.getPublicationsForUserBySubscriptions(userId)
                .stream()
                .map(PublicationDto::from)
                .collect(Collectors.toList());
    }

    public PublicationDto addPublication(String img, String descr, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        int userId = publicationDao.findUserByUserEmail(user.getUsername());
        var publication = Publication.builder()
                .id(publicationDao.getAllPubs().size() + 1)
                .userID(userId)
                .img(img)
                .description(descr)
                .timeOfPublication(LocalDateTime.now())
                .build();

        publicationDao.save(publication);
        return PublicationDto.from(publication);
    }
    public boolean deletePublication(Long commentId) {
        //TODO recalculate movie rating before delete
        publicationDao.deleteById(commentId);
        return true;
    }
}
