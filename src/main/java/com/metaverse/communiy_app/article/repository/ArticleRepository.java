package com.metaverse.communiy_app.article.repository;

import com.metaverse.communiy_app.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
