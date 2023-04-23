package com.example.microgram.controller;

import com.example.microgram.dto.CommentDto;
import com.example.microgram.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public CommentDto addComment(@RequestParam int publicationId, @RequestParam String text, Authentication authentication){
        return commentService.addComment(authentication, publicationId, text);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, Authentication authentication) {
        if (commentService.checkFotDeleteComment(authentication,id)) {
            commentService.deleteComment(authentication, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
