package com.metaverse.community_app.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.comment.dto.CommentResponseDto; // 댓글 DTO 임포트
import com.metaverse.community_app.file.dto.FileResponseDto; // 파일 DTO 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String boardTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> comments;
    private List<FileResponseDto> files;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createAt = article.getCreatedAt();
        this.modifiedAt = article.getModifiedAt();

        this.boardTitle = article.getBoard().getTitle();

        if (article.getComments() != null) {
            this.comments = article.getComments().stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            this.comments = List.of();
        }

        if (article.getFiles() != null) {
            this.files = article.getFiles().stream()
                    .map(FileResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            this.files = List.of();
        }
    }
}