package com.example.microgram.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class Like {
    private int id;
    private int likedPublicationId;
    private LocalDateTime lickedTime;
    private int userId;

    public Like(int id, int likedPublication, LocalDateTime lickedTime, int userID) {
        this.id = id;
        this.likedPublicationId = likedPublication;
        this.lickedTime = lickedTime;
        this.userId = userID;
    }
}
