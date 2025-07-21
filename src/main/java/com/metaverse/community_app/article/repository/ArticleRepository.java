package com.metaverse.community_app.article.repository;

import com.metaverse.community_app.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreatedAtDesc();

    List<Article> findByBoardIdOrderByCreatedAtDesc(Long boardId);

    Optional<Article> findByIdAndBoardId(Long articleId, Long boardId);
}