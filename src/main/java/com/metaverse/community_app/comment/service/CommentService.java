package com.metaverse.community_app.comment.service;

import com.metaverse.community_app.comment.domain.Comment;
import com.metaverse.community_app.comment.dto.CommentRequestDto;
import com.metaverse.community_app.comment.dto.CommentResponseDto;
import com.metaverse.community_app.comment.repository.CommentRepository;
import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Article article = articleRepository.findById(commentRequestDto.getArticleId()).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.")
        );
        Comment comment = new Comment(
                commentRequestDto.getContent(),
                article
        );
        Comment savedComment = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(savedComment);
        return commentResponseDto;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments() {
        List<CommentResponseDto> commentResponseDtoList = commentRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
        return commentResponseDtoList;
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long id) {
        Comment comment = findComment(id);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(id);
        comment.update(
                commentRequestDto.getContent()
        );
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = findComment(id);
        commentRepository.delete(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다.")
        );
    }
}
