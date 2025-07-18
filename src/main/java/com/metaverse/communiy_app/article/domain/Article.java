package com.metaverse.communiy_app.article.domain;

import com.metaverse.communiy_app.article.dto.ArticleRequestDto;
import com.metaverse.communiy_app.common.domain.TimeStamped;
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

    public Article(ArticleRequestDto articleRequestDto) {
        this.title = articleRequestDto.getTitle();
        this.content = articleRequestDto.getContent();
    }

    public void update(ArticleRequestDto articleRequestDto) {
        this.title = articleRequestDto.getTitle();
        this.content = articleRequestDto.getContent();
    }
}
