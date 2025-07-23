package com.metaverse.community_app.likes.articleLike.domain;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.common.domain.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "article_like", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "article_id"})
}) // 좋아요 중복 금지를 위한 유니크 제약조건
public class ArticleLike extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ArticleLike(Article article, User user) {
        this.article = article;
        this.user = user;
    }
}