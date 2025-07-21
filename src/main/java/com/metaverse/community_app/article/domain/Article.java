package com.metaverse.community_app.article.domain;

import com.metaverse.community_app.board.domain.Board;
import com.metaverse.community_app.common.domain.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    public Article(String title, String content, Board board) {
        this.title = title;
        this.content = content;
        this.board = board;
    }

    public void update(String title, String content)  {
        this.title = title;
        this.content = content;
    }
}
