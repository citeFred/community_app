package com.metaverse.community_app.article.dto;

import lombok.Getter;

@Getter
public class ArticleRequestDto {
    private Long boardId;
    private String title;
    private String content;
}
