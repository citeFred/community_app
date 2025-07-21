package com.metaverse.communiy_app.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaverse.communiy_app.article.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String boardTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createAt = article.getCreatedAt();
        this.modifiedAt = article.getModifiedAt();
        this.boardTitle = article.getBoard().getTitle(); // 연관관계 정보 가져오기(Lazy Loading) 실행부
    }
}
