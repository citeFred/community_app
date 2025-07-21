package com.metaverse.community_app.comment.service;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.service.ArticleService;
import com.metaverse.community_app.comment.domain.Comment;
import com.metaverse.community_app.comment.dto.CommentRequestDto;
import com.metaverse.community_app.comment.dto.CommentResponseDto;
import com.metaverse.community_app.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    public CommentService(CommentRepository commentRepository, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
    }

    @Transactional
    public CommentResponseDto createComment(Long boardId, Long articleId, CommentRequestDto commentRequestDto) {
        Article article = articleService.getValidBoardAndArticle(boardId, articleId);

        Comment comment;
        if (commentRequestDto.getParentCommentId() != null) {
            Comment parentComment = getValidParentComment(commentRequestDto.getParentCommentId(), article);
            comment = new Comment(commentRequestDto.getContent(), article, parentComment);
        } else {
            comment = new Comment(commentRequestDto.getContent(), article);
        }

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments() {
        return commentRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByArticleId(Long boardId, Long articleId) {
        articleService.getValidBoardAndArticle(boardId, articleId);

        return commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long boardId, Long articleId, Long commentId) {
        Comment comment = getValidComment(boardId, articleId, commentId);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long boardId, Long articleId, Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = getValidComment(boardId, articleId, commentId);
        comment.update(
                commentRequestDto.getContent()
        );
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long boardId, Long articleId, Long commentId) {
        Comment comment = getValidComment(boardId, articleId, commentId);
        commentRepository.delete(comment);
    }

    private Comment getValidParentComment(Long parentCommentId, Article article) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다. Comment ID: " + parentCommentId));

        if (!parentComment.getArticle().getId().equals(article.getId())) {
            throw new IllegalArgumentException("대댓글은 부모 댓글과 동일한 게시글에 속해야 합니다.");
        }
        return parentComment;
    }

    private Comment getValidComment(Long boardId, Long articleId, Long commentId) {
        articleService.getValidBoardAndArticle(boardId, articleId);

        return commentRepository.findByIdAndArticleId(commentId, articleId).orElseThrow(() ->
                new IllegalArgumentException("게시글(ID: " + articleId + ")에서 댓글(ID: " + commentId + ")을 찾을 수 없습니다.")
        );
    }
}