package com.example.roomie.domain.post.repository;

import com.roommate.roommate.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdAndUserId(Long commentId, Long userId);

    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.user u WHERE u.id = :userId AND u.isDeleted = false")
    List<Comment> findByUserIdAndIsDeletedIsFalse(@Param("userId")Long userId);
    Comment findByIdAndUserIdAndIsDeletedIsFalse(Long commentId, Long userId);
}
