package com.metaverse.community_app.comment.domain;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.board.domain.Board;
import com.metaverse.community_app.common.domain.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    public Comment(String content, Article article) {
        this.content = content;
        this.article = article;
    }

    public void update(String content) {
        this.content = content;
    }
}
