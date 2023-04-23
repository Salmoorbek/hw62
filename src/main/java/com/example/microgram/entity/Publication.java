package com.example.microgram.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class Publication {
    private int id;
    private String img;
    private String description;
    private LocalDateTime timeOfPublication;
    private int userID;

    public Publication(int id, String img, String description, LocalDateTime timeOfPublication, int userID) {
        this.id = id;
        this.img = img;
        this.description = description;
        this.timeOfPublication = timeOfPublication;
        this.userID = userID;
    }
}
