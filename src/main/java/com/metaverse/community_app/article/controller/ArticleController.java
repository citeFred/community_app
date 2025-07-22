package com.metaverse.community_app.article.controller;

import com.metaverse.community_app.article.dto.ArticleRequestDto;
import com.metaverse.community_app.article.dto.ArticleResponseDto;
import com.metaverse.community_app.article.service.ArticleService;
import com.metaverse.community_app.auth.domain.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/boards/{boardId}/articles")
    public ResponseEntity<ArticleResponseDto> createArticleForBoard(
            @PathVariable Long boardId,
            @RequestPart("articleData") ArticleRequestDto articleRequestDto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ArticleResponseDto articleResponseDto = articleService.createArticle(principalDetails, boardId, articleRequestDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDto);
    }

    @GetMapping("/boards/{boardId}/articles")
    public ResponseEntity<List<ArticleResponseDto>> getArticlesByBoardId(
            @PathVariable Long boardId) {
        List<ArticleResponseDto> articleResponseDtoList = articleService.getArticlesByBoardId(boardId);
        return ResponseEntity.ok(articleResponseDtoList);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponseDto>> getArticles() {
        List<ArticleResponseDto> articleResponseDtoList = articleService.getArticles();
        return ResponseEntity.ok(articleResponseDtoList);
    }

    @GetMapping("/boards/{boardId}/articles/{id}")
    public ResponseEntity<ArticleResponseDto> getArticleById(
            @PathVariable Long boardId,
            @PathVariable Long id) {
        ArticleResponseDto articleResponseDto = articleService.getArticleById(boardId, id);
        return ResponseEntity.ok(articleResponseDto);
    }

    @PutMapping("/boards/{boardId}/articles/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(
            @PathVariable Long boardId,
            @PathVariable Long id,
            @RequestBody ArticleRequestDto articleRequestDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        ArticleResponseDto updatedArticle = articleService.updateArticle(principalDetails, boardId, id, articleRequestDto);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/boards/{boardId}/articles/{id}")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable Long boardId,
            @PathVariable Long id,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        articleService.deleteArticle(principalDetails, boardId, id);
        return ResponseEntity.noContent().build();
    }
}