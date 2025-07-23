package com.metaverse.community_app.likes.commentLike.service;

import com.metaverse.community_app.auth.domain.PrincipalDetails;
import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.comment.domain.Comment;
import com.metaverse.community_app.comment.service.CommentService;
import com.metaverse.community_app.likes.commentLike.domain.CommentLike;
import com.metaverse.community_app.likes.commentLike.dto.CommentLikeResponseDto;
import com.metaverse.community_app.likes.commentLike.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

    @Transactional
    public CommentLikeResponseDto toggleCommentLike(PrincipalDetails principalDetails, Long boardId, Long articleId, Long commentId) {
        Comment comment = commentService.getValidComment(boardId, articleId, commentId);

        User currentUser = principalDetails.getUser();

        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentAndUser(comment, currentUser);

        boolean liked;
        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            liked = false;
        } else {
            CommentLike newLike = new CommentLike(comment, currentUser);
            commentLikeRepository.save(newLike);
            liked = true;
        }

        int currentLikesCount = (int) commentLikeRepository.countByComment(comment);

        return new CommentLikeResponseDto(comment.getId(), currentUser.getId(), liked, currentLikesCount);
    }
}