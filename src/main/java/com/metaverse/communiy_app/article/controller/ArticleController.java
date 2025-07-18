package com.metaverse.communiy_app.article.controller;

import com.metaverse.communiy_app.article.dto.ArticleRequestDto;
import com.metaverse.communiy_app.article.dto.ArticleResponseDto;
import com.metaverse.communiy_app.article.service.ArticleService;
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
    public ArticleResponseDto createArticle(@RequestBody ArticleRequestDto articleRequestDto) {
        return articleService.createArticle(articleRequestDto);
    }

    @GetMapping("/articles")
    public List<ArticleResponseDto> getArticles() {
        return articleService.getArticles();
    }

    @PutMapping("/articles/{id}")
    public Long updateArticle(@PathVariable Long id, @RequestBody ArticleRequestDto articleRequestDto) {
        return articleService.updateArticle(id, articleRequestDto);
    }

    @DeleteMapping("/articles/{id}")
    public Long deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }
}
