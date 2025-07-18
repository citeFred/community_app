package com.metaverse.communiy_app.article.service;

import com.metaverse.communiy_app.article.domain.Article;
import com.metaverse.communiy_app.article.dto.ArticleRequestDto;
import com.metaverse.communiy_app.article.dto.ArticleResponseDto;
import com.metaverse.communiy_app.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto articleRequestDto) {
        Article article = new Article(articleRequestDto);
        Article savedArticle = articleRepository.save(article);
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(savedArticle);
        return articleResponseDto;
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getArticles() {
        List<ArticleResponseDto> responseList = articleRepository.findAllByOrderByCreatedAtDesc().stream().map(ArticleResponseDto::new).toList();
        return responseList;
    }

    @Transactional
    public Long updateArticle(Long id, ArticleRequestDto articleRequestDto) {
        Article article = findArticle(id);
        article.update(articleRequestDto);
        return id;
    }

    @Transactional
    public Long deleteArticle(Long id) {
        Article article = findArticle(id);
        articleRepository.delete(article);
        return id;
    }

    private Article findArticle(Long id) {
        return articleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
        );
    }
}
