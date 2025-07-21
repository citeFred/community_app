package com.metaverse.communiy_app.article.controller;

import com.metaverse.communiy_app.article.dto.ArticleRequestDto;
import com.metaverse.communiy_app.article.dto.ArticleResponseDto;
import com.metaverse.communiy_app.article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto articleRequestDto) {
        ArticleResponseDto articleResponseDto = articleService.createArticle(articleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponseDto>> getArticles() {
        List<ArticleResponseDto> articleResponseDtoList = articleService.getArticles();
        return ResponseEntity.ok(articleResponseDtoList);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable Long id) {
        ArticleResponseDto articleResponseDto = articleService.getArticleById(id);
        return ResponseEntity.ok(articleResponseDto);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id, @RequestBody ArticleRequestDto articleRequestDto) {
        ArticleResponseDto updatedArticle = articleService.updateArticle(id, articleRequestDto);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
