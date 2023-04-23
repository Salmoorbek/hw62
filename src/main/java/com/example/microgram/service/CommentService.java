package com.example.microgram.service;

import com.example.microgram.dao.CommentDao;
import com.example.microgram.dto.CommentDto;
import com.example.microgram.entity.Comment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public CommentDto addComment(Authentication authentication , int pubsId, String text) {
        User user = (User) authentication.getPrincipal();
        int userId = commentDao.findUserByUserEmail(user.getUsername());
        var comment = Comment.builder()
                .userId(userId)
                .publicationId(pubsId)
                .commentText(text)
                .timeOfComment(LocalDateTime.now())
                .build();

        commentDao.addComment(comment);
        return CommentDto.from(comment);
    }
    public void deleteComment(Authentication authentication, Long commentId) {
        User user = (User) authentication.getPrincipal();
        commentDao.deleteById(commentId, user.getUsername());
    }
    public boolean checkFotDeleteComment(Authentication authentication, Long commentId) {
        User user = (User) authentication.getPrincipal();
        return commentDao.checkForDelete(commentId, user.getUsername()) != 0;
    }
}
