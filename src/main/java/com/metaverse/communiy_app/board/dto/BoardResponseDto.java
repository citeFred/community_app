package com.metaverse.communiy_app.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaverse.communiy_app.article.dto.ArticleResponseDto;
import com.metaverse.communiy_app.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;
    private List<ArticleResponseDto> articles;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.articles = board.getArticles()
                .stream()
                .map(ArticleResponseDto::new)
                .collect(Collectors.toList()
                );
    }
}
