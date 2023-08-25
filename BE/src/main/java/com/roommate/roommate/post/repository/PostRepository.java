package com.roommate.roommate.post.repository;


import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.domain.PostArea;
import com.roommate.roommate.post.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryAndIsDeletedIsFalse(PostCategory postCategory);
    List<Post> findByAreaAndIsDeletedIsFalse(PostArea postArea);
    Post findByUserIdAndIsDeletedIsFalse(Long userId);
    Post findByIdAndIsDeletedIsFalse(Long postId);
    Post findByIdAndUserId(Long postId, Long userId);
}
