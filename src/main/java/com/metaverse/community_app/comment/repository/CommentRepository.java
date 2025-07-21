package com.metaverse.community_app.comment.repository;

import com.metaverse.community_app.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();
}
