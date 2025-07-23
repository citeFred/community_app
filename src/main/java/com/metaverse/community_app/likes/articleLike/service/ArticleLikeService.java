package com.metaverse.community_app.likes.articleLike.service;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.service.ArticleService;
import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.likes.articleLike.domain.ArticleLike;
import com.metaverse.community_app.likes.articleLike.dto.ArticleLikeResponseDto;
import com.metaverse.community_app.likes.articleLike.repository.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleService articleService;

    @Transactional
    public ArticleLikeResponseDto toggleArticleLike(PrincipalDetails principalDetails, Long boardId, Long articleId) {
        Article article = articleService.getValidBoardAndArticle(boardId, articleId);

        User currentUser = principalDetails.getUser();

        Optional<ArticleLike> existingLike = articleLikeRepository.findByArticleAndUser(article, currentUser);

        boolean liked;
        if (existingLike.isPresent()) {
            articleLikeRepository.delete(existingLike.get());
            liked = false;
        } else {
            ArticleLike newLike = new ArticleLike(article, currentUser);
            articleLikeRepository.save(newLike);
            liked = true;
        }

        int currentLikesCount = (int) articleLikeRepository.countByArticle(article);

        return new ArticleLikeResponseDto(article.getId(), currentUser.getId(), liked, currentLikesCount);
    }
}