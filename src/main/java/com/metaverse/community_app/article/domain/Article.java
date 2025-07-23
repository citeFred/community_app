package com.metaverse.community_app.article.domain;

import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.board.domain.Board;
import com.metaverse.community_app.comment.domain.Comment;
import com.metaverse.community_app.common.domain.TimeStamped;
import com.metaverse.community_app.file.domain.File;
import com.metaverse.community_app.likes.articleLike.domain.ArticleLike;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "article")
@Entity
public class Article extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000, nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleLike> articleLikes = new ArrayList<>();

    public Article(String title, String content, Board board, User user) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.user = user;
    }

    public void update(String title, String content)  {
        this.title = title;
        this.content = content;
    }
}
