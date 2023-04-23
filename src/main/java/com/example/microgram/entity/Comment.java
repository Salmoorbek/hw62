package com.example.microgram.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class Comment {
    private int id;
    private int userId;
    private int publicationId;
    private String commentText;
    private LocalDateTime timeOfComment;

    public Comment(int id, int userId, int publicationId, String commentText, LocalDateTime timeOfComment) {
        this.id = id;
        this.userId = userId;
        this.publicationId = publicationId;
        this.commentText = commentText;
        this.timeOfComment = timeOfComment;
    }
}
