package com.example.roomie.domain.user.repository;

import com.example.roomie.domain.user.User;
import com.example.roomie.domain.user.enums.Gender;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdAndIsDeletedIsFalse(Long id);

    User findByUidAndIsDeletedIsFalse(String uid);
    List<User> findByGender(Gender gender);
    @EntityGraph(attributePaths = "posts")
    @Query("SELECT u FROM User u WHERE u.gender = :gender")
    List<User> findAllByGender(@Param("gender") Gender gender);

    boolean existsByUid(String uid);
    boolean existsByEmail(String Email);
}
