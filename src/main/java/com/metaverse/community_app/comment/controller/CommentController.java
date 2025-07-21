package com.metaverse.community_app.comment.controller;

import com.metaverse.community_app.comment.dto.CommentRequestDto;
import com.metaverse.community_app.comment.dto.CommentResponseDto;
import com.metaverse.community_app.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        List<CommentResponseDto> commentResponseDtoList = commentService.getComments();
        return ResponseEntity.ok(commentResponseDtoList);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        CommentResponseDto commentResponseDto = commentService.getCommentById(id);
        return ResponseEntity.ok(commentResponseDto);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(id, commentRequestDto);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
