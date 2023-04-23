package com.example.microgram.dto;

import com.example.microgram.entity.Like;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class LikeDto {
    private int likedPublicationId;
    private LocalDateTime lickedTime;
    private int userId;

    public static LikeDto from(Like like) {
        return builder()
                .userId(like.getUserId())
                .likedPublicationId(like.getLikedPublicationId())
                .lickedTime(like.getLickedTime())
                .build();
    }
}
