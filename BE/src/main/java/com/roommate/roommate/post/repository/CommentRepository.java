package com.roommate.roommate.post.repository;

import com.roommate.roommate.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdAndUserId(Long commentId, Long userId);

    List<Comment> findByUserIdAndIsDeletedIsFalse(Long userId);
    Comment findByIdAndUserIdAndIsDeletedIsFalse(Long commentId, Long userId);
}
