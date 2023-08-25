package com.roommate.roommate.post.repository;

import com.roommate.roommate.post.domain.LikedPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikedRepository extends JpaRepository<LikedPhoto, Long> {
    List<LikedPhoto> findByUserIdAndIsDeletedIsFalse(Long userId);
    LikedPhoto findByIdAndUserId(Long postLikedId, Long userId);
    LikedPhoto findByUserIdAndPostIdAndIsDeletedIsFalse(Long userId, Long postId);
}
