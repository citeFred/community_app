package com.metaverse.community_app.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaverse.community_app.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long articleId;
    private Long parentCommentId;
    private boolean liked;
    private int likesCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment, int likesCount, boolean liked) {
        this.id = comment.getId();
        this.content = comment.getContent();

        if (comment.getArticle() != null) {
            this.articleId = comment.getArticle().getId();
        } else {
            this.articleId = null;
        }

        this.parentCommentId = (comment.getParentComment() != null) ? comment.getParentComment().getId() : null;

        this.likesCount = likesCount;
        this.liked = liked;

        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();

        if (comment.getArticle() != null) {
            this.articleId = comment.getArticle().getId();
        } else {
            this.articleId = null;
        }

        this.parentCommentId = (comment.getParentComment() != null) ? comment.getParentComment().getId() : null;

        this.likesCount = 0;
        this.liked = false;

        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}