package com.metaverse.community_app.comment.controller;

import com.metaverse.community_app.comment.dto.CommentRequestDto;
import com.metaverse.community_app.comment.dto.CommentResponseDto;
import com.metaverse.community_app.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/articles/{articleId}/comments")
    public ResponseEntity<CommentResponseDto> createCommentForArticle(
            @PathVariable Long boardId,
            @PathVariable Long articleId,
            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.createComment(boardId, articleId, commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
    }

    @GetMapping("/boards/{boardId}/articles/{articleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByArticleId(
            @PathVariable Long boardId,
            @PathVariable Long articleId) {
        List<CommentResponseDto> commentResponseDtoList = commentService.getCommentsByArticleId(boardId, articleId);
        return ResponseEntity.ok(commentResponseDtoList);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        List<CommentResponseDto> commentResponseDtoList = commentService.getComments();
        return ResponseEntity.ok(commentResponseDtoList);
    }

    @GetMapping("/boards/{boardId}/articles/{articleId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @PathVariable Long boardId,
            @PathVariable Long articleId,
            @PathVariable Long id) {
        CommentResponseDto commentResponseDto = commentService.getCommentById(boardId, articleId, id);
        return ResponseEntity.ok(commentResponseDto);
    }

    @PutMapping("/boards/{boardId}/articles/{articleId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long boardId,
            @PathVariable Long articleId,
            @PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(boardId, articleId, id, commentRequestDto);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/boards/{boardId}/articles/{articleId}/comments/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long articleId,
            @PathVariable Long id) {
        commentService.deleteComment(boardId, articleId, id);
        return ResponseEntity.noContent().build();
    }
}