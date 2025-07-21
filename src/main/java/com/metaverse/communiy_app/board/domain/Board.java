package com.metaverse.communiy_app.board.domain;

import com.metaverse.communiy_app.article.domain.Article;
import com.metaverse.communiy_app.board.dto.BoardRequestDto;
import com.metaverse.communiy_app.common.domain.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
@Entity
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Article> articles =  new ArrayList<>();


    public Board(String title) {
        this.title = title;
    }

    public void update(String title) {
        this.title = title;
    }
}
