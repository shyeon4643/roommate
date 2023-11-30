package com.roommate.roommate.post.repository;


import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.domain.PostArea;
import com.roommate.roommate.post.domain.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT DISTINCT p.*, pp.*, c.* " +
            "FROM Post p " +
            "LEFT JOIN post_photo pp ON p.post_id = pp.post_id " +
            "LEFT JOIN comment c ON p.post_id = c.post_id " +
            "WHERE p.category = :postCategory AND p.is_deleted = false", nativeQuery = true)
    Page<Post> findByCategoryAndIsDeletedIsFalse(@Param("postCategory") String postCategory, Pageable pageable);
    @Query(value = "SELECT DISTINCT p.*, pp.*, c.*, u.* " +
            "FROM Post p " +
            "LEFT JOIN post_photo pp ON p.post_id = pp.post_id " +
            "LEFT JOIN comment c ON p.post_id = c.post_id " +
            "LEFT JOIN user u ON c.user_id = u.user_id " +
            "WHERE p.area = :postArea AND p.is_deleted = false", nativeQuery = true)
    List<Post> findByAreaAndIsDeletedIsFalse(@Param("postArea")String postArea);


    Post findByUserIdAndIsDeletedIsFalse(Long userId);

    @Query("SELECT p from Post p where p.isDeleted = false ORDER BY p.createdAt DESC")
    Slice<Post> findAllByOrderByCreatedAtDescAndIsDeletedIsFalse(Pageable pageable);

    Post findByUserId(Long userId);
    Post findByIdAndUserId(Long postId, Long userId);

    @Query("SELECT DISTINCT p " +
            "FROM Post p " +
            "WHERE p.title LIKE %:keyword% " +
            "OR CONCAT(p.area, '') LIKE %:keyword%")
    Page<Post> findByTitleContainingOrAreaContaining(@Param("keyword")String keyword, Pageable pageable);
}
