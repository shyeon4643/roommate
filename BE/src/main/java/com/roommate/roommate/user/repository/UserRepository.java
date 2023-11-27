package com.roommate.roommate.user.repository;


import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.domain.enums.Gender;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUid(String uid);

    @Query("SELECT u FROM User u WHERE u.uid = :uid AND u.isDeleted = false")
    User findByUidAndIsDeletedIsFalse(@Param("uid")String uid);
    @EntityGraph(attributePaths = "posts")
    @Query("SELECT u FROM User u WHERE u.gender = :gender")
    List<User> findAllByGender(@Param("gender") Gender gender);

    boolean existsByUid(String uid);
    boolean existsByEmail(String Email);

    Optional<User> findByKakaoId(Long kakaoId);
}
