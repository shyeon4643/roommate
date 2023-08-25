package com.roommate.roommate.post.repository;

import com.roommate.roommate.post.domain.PostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {
}
