package com.metaverse.community_app.article.repository;

import com.metaverse.community_app.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByIdAndBoardId(Long articleId, Long boardId);

    Page<Article> findByBoardId(Long boardId, Pageable pageable);

    Page<Article> findAll(Pageable pageable);
}