package com.metaverse.community_app.comment.repository;

import com.metaverse.community_app.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();

    List<Comment> findByArticleIdOrderByCreatedAtDesc(Long articleId);

    Optional<Comment> findByIdAndArticleId(Long commentId, Long articleId);
}