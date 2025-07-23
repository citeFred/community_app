package com.metaverse.community_app.likes.commentLike.repository;

import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.comment.domain.Comment;
import com.metaverse.community_app.likes.commentLike.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);

    long countByComment(Comment comment);
}