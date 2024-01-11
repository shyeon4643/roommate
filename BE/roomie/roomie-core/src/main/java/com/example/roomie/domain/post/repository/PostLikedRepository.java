package com.example.roomie.domain.post.repository;

import com.roommate.roommate.post.domain.LikedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikedRepository extends JpaRepository<LikedPost, Long> {
    List<LikedPost> findByUserIdAndIsDeletedIsFalse(Long userId);
    LikedPost findByIdAndUserId(Long postLikedId, Long userId);
    LikedPost findByUserIdAndPostId(Long userId, Long postId);
}
