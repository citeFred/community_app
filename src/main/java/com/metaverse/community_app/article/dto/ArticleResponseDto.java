package com.metaverse.community_app.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.comment.dto.CommentResponseDto;
import com.metaverse.community_app.file.dto.FileResponseDto;
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
    private String authorUsername;
    private String authorNickname;
    private boolean liked;
    private int likesCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> comments;
    private List<FileResponseDto> files;

    public ArticleResponseDto(Article article, int likesCount, boolean liked) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createAt = article.getCreatedAt();
        this.modifiedAt = article.getModifiedAt();

        if (article.getBoard() != null) {
            this.boardTitle = article.getBoard().getTitle();
        }

        if (article.getUser() != null) {
            this.authorUsername = article.getUser().getUsername();
            this.authorNickname = article.getUser().getNickname();
        }

        this.likesCount = likesCount;
        this.liked = liked;

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

    // 목록 조회 시 사용되는 생성자 (좋아요 수/여부 없이 간략화)
    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createAt = article.getCreatedAt();
        this.modifiedAt = article.getModifiedAt();

        if (article.getBoard() != null) {
            this.boardTitle = article.getBoard().getTitle();
        }

        if (article.getUser() != null) {
            this.authorUsername = article.getUser().getUsername();
            this.authorNickname = article.getUser().getNickname();
        }

        this.likesCount = 0;
        this.liked = false;

        this.comments = List.of(); // 목록 조회 시에는 comments는 제외 (N+1 방지)
        this.files = List.of();    // 목록 조회 시에는 files도 제외 (N+1 방지)
    }
}