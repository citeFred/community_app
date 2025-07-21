package com.metaverse.community_app.comment.service;

import com.metaverse.community_app.comment.domain.Comment;
import com.metaverse.community_app.comment.dto.CommentRequestDto;
import com.metaverse.community_app.comment.dto.CommentResponseDto;
import com.metaverse.community_app.comment.repository.CommentRepository;
import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.repository.ArticleRepository;
import com.metaverse.community_app.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public CommentResponseDto createComment(Long boardId, Long articleId, CommentRequestDto commentRequestDto) {
        Article article = getValidArticleFromBoardAndArticleId(boardId, articleId);

        Comment comment = new Comment(
                commentRequestDto.getContent(),
                article
        );
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
        getValidArticleFromBoardAndArticleId(boardId, articleId);

        return commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long boardId, Long articleId, Long commentId) {
        Comment comment = findComment(boardId, articleId, commentId);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long boardId, Long articleId, Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(boardId, articleId, commentId);
        comment.update(
                commentRequestDto.getContent()
        );
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long boardId, Long articleId, Long commentId) {
        Comment comment = findComment(boardId, articleId, commentId);
        commentRepository.delete(comment);
    }

    private Article getValidArticleFromBoardAndArticleId(Long boardId, Long articleId) {
        boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시판을 찾을 수 없습니다. Board ID: " + boardId)
        );
        return articleRepository.findByIdAndBoardId(articleId, boardId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시판(ID: " + boardId + ")에서 게시글(ID: " + articleId + ")을 찾을 수 없습니다.")
        );
    }

    private Comment findComment(Long boardId, Long articleId, Long commentId) {
        Article article = getValidArticleFromBoardAndArticleId(boardId, articleId);

        return commentRepository.findByIdAndArticleId(commentId, article.getId()).orElseThrow(() ->
                new IllegalArgumentException("게시글(ID: " + articleId + ")에서 댓글(ID: " + commentId + ")을 찾을 수 없습니다.")
        );
    }
}