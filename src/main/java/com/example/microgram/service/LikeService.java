package com.example.microgram.service;

import com.example.microgram.dao.LikeDao;
import com.example.microgram.dto.LikeDto;
import com.example.microgram.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;

    public String isUserLikedPublications(int likedPublicationId) {
        return likeDao.userLikedPublication(likedPublicationId);
    }

    public LikeDto likePublication(int userId, int publicationId) {
        var like = Like.builder()
                .id(likeDao.getAllLikes().size() + 1)
                .userId(userId)
                .likedPublicationId(publicationId)
                .lickedTime(LocalDateTime.now())
                .build();

        likeDao.save(like);
        return LikeDto.from(like);
    }
}
