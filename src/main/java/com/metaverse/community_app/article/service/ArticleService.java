package com.metaverse.community_app.article.service;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.dto.ArticleRequestDto;
import com.metaverse.community_app.article.dto.ArticleResponseDto;
import com.metaverse.community_app.article.repository.ArticleRepository;
import com.metaverse.community_app.board.domain.Board;
import com.metaverse.community_app.board.repository.BoardRepository;
import com.metaverse.community_app.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final FileService fileService;

    @Transactional
    public ArticleResponseDto createArticle(Long boardId, ArticleRequestDto articleRequestDto, MultipartFile file) {
        Board board = getValidBoard(boardId);

        Article article = new Article(
                articleRequestDto.getTitle(),
                articleRequestDto.getContent(),
                board
        );
        Article savedArticle = articleRepository.save(article);

        if (file != null && !file.isEmpty()) {
            fileService.uploadFile(savedArticle, file);
        }

        return new ArticleResponseDto(savedArticle);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(ArticleResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getArticlesByBoardId(Long boardId) {
        getValidBoard(boardId);

        return articleRepository.findByBoardIdOrderByCreatedAtDesc(boardId).stream()
                .map(ArticleResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticleById(Long boardId, Long articleId) {
        Article article = getValidBoardAndArticle(boardId, articleId);
        return new ArticleResponseDto(article);
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long boardId, Long articleId, ArticleRequestDto articleRequestDto) {
        Article article = getValidBoardAndArticle(boardId, articleId);
        article.update(
                articleRequestDto.getTitle(),
                articleRequestDto.getContent()
        );
        return new ArticleResponseDto(article);
    }

    @Transactional
    public void deleteArticle(Long boardId, Long articleId) {
        Article article = getValidBoardAndArticle(boardId, articleId);
        articleRepository.delete(article);
    }

    public Board getValidBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시판을 찾을 수 없습니다. Board ID: " + boardId)
        );
    }

    public Article getValidBoardAndArticle(Long boardId, Long articleId) {
        getValidBoard(boardId);

        return articleRepository.findByIdAndBoardId(articleId, boardId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시판(ID: " + boardId + ")에서 게시글(ID: " + articleId + ")을 찾을 수 없습니다.")
        );
    }
}