package com.metaverse.community_app.comment.domain;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.common.domain.TimeStamped;
import com.metaverse.community_app.likes.articleLike.domain.ArticleLike;
import com.metaverse.community_app.likes.commentLike.domain.CommentLike;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(String content, Article article) {
        this.content = content;
        this.article = article;
        this.parentComment = null;
    }

    public Comment(String content, Article article, Comment parentComment) {
        if (parentComment == null) {
            throw new IllegalArgumentException("부모 댓글은 null이 될 수 없습니다. 최상위 댓글은 다른 생성자를 사용하세요.");
        }
        if (!article.getId().equals(parentComment.getArticle().getId())) {
            throw new IllegalArgumentException("대댓글은 부모 댓글과 동일한 게시글에 속해야 합니다.");
        }
        this.content = content;
        this.article = article;
        this.parentComment = parentComment;
    }

    public void update(String content) {
        this.content = content;
    }

    public boolean isTopLevelComment() {
        return this.parentComment == null;
    }
}