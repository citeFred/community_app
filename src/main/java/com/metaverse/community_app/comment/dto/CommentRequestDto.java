package com.metaverse.community_app.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long articleId;
    private String content;
}
