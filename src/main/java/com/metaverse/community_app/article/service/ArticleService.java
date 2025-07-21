package com.metaverse.community_app.article.service;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.dto.ArticleRequestDto;
import com.metaverse.community_app.article.dto.ArticleResponseDto;
import com.metaverse.community_app.article.repository.ArticleRepository;
import com.metaverse.community_app.board.domain.Board;
import com.metaverse.community_app.board.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) {
        Board board = boardRepository.findById(articleRequestDto.getBoardId()).orElseThrow(() ->
                new IllegalArgumentException("해당 게시판을 찾을 수 없습니다.")
        );
        Article article = new Article(
                articleRequestDto.getTitle(),
                articleRequestDto.getContent(),
                board
        );
        Article savedArticle = articleRepository.save(article);
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(savedArticle);
        return articleResponseDto;
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getArticles() {
        List<ArticleResponseDto> articleResponseDtoList = articleRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ArticleResponseDto::new)
                .toList();
        return articleResponseDtoList;
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticleById(Long id) {
        Article article = findArticle(id);
        return new ArticleResponseDto(article);
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long id, ArticleRequestDto articleRequestDto) {
        Article article = findArticle(id);
        article.update(
                articleRequestDto.getTitle(),
                articleRequestDto.getContent()
        );
        return new ArticleResponseDto(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = findArticle(id);
        articleRepository.delete(article);
    }

    private Article findArticle(Long id) {
        return articleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
        );
    }
}
