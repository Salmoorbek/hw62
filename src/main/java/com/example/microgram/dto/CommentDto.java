package com.example.microgram.dto;

import com.example.microgram.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CommentDto {
    private int userId;
    private int publicationId;
    private String commentText;
    private LocalDateTime timeOfComment;

    public static CommentDto from(Comment comment){
        return builder()
                .userId(comment.getUserId())
                .publicationId(comment.getPublicationId())
                .commentText(comment.getCommentText())
                .timeOfComment(comment.getTimeOfComment())
                .build();
    }
}
