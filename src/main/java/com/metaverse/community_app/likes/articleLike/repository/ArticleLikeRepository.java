package com.metaverse.community_app.likes.articleLike.repository;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.likes.articleLike.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Optional<ArticleLike> findByArticleAndUser(Article article, User user);

    long countByArticle(Article article);
}