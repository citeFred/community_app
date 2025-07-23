package com.metaverse.community_app.article.service;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.dto.ArticleRequestDto;
import com.metaverse.community_app.article.dto.ArticleResponseDto;
import com.metaverse.community_app.article.repository.ArticleRepository;
import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.board.domain.Board;
import com.metaverse.community_app.board.repository.BoardRepository;
import com.metaverse.community_app.file.service.FileService;
import com.metaverse.community_app.likes.articleLike.repository.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final ArticleLikeRepository articleLikeRepository;

    @Transactional
    public ArticleResponseDto createArticle(PrincipalDetails principalDetails, Long boardId, ArticleRequestDto articleRequestDto, MultipartFile file) {
        User logginedUser = principalDetails.getUser();
        Board board = getValidBoard(boardId);

        Article article = new Article(
                articleRequestDto.getTitle(),
                articleRequestDto.getContent(),
                board,
                logginedUser
        );
        Article savedArticle = articleRepository.save(article);

        if (file != null && !file.isEmpty()) {
            fileService.uploadFile(savedArticle, file);
        }

        return new ArticleResponseDto(savedArticle);
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponseDto> getArticles(Pageable pageable) {
        Page<Article> articlePage = articleRepository.findAll(pageable);
        return articlePage.map(ArticleResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponseDto> getArticlesByBoardId(Long boardId, Pageable pageable) {
        getValidBoard(boardId);
        Page<Article> articlePage = articleRepository.findByBoardId(boardId, pageable);
        return articlePage.map(ArticleResponseDto::new);
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticleById(PrincipalDetails principalDetails, Long boardId, Long articleId) {
        Article article = getValidBoardAndArticle(boardId, articleId);

        int currentLikesCount = (int) articleLikeRepository.countByArticle(article);
        boolean liked = false;
        System.out.println(principalDetails.getUser());
        if (principalDetails != null) {
            User currentUser = principalDetails.getUser();
            liked = articleLikeRepository.findByArticleAndUser(article, currentUser).isPresent();
        }

        return new ArticleResponseDto(article, currentLikesCount, liked);
    }

    @Transactional
    public ArticleResponseDto updateArticle(PrincipalDetails principalDetails, Long boardId, Long articleId, ArticleRequestDto articleRequestDto) {
        Article article = getValidBoardAndArticle(boardId, articleId);
        checkArticleOwnership(article, principalDetails);

        article.update(
                articleRequestDto.getTitle(),
                articleRequestDto.getContent()
        );
        return new ArticleResponseDto(article);
    }

    @Transactional
    public void deleteArticle(PrincipalDetails principalDetails, Long boardId, Long articleId) {
        Article article = getValidBoardAndArticle(boardId, articleId);
        checkArticleOwnership(article, principalDetails);

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

    private void checkArticleOwnership(Article article, PrincipalDetails principalDetails) {
        if (!article.getUser().getId().equals(principalDetails.getUser().getId())) {
            throw new IllegalArgumentException("게시글은 작성자만 수정하거나 삭제할 수 있습니다.");
        }
    }
}