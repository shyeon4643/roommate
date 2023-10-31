package com.roommate.roommate.user.repository;


import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.domain.enums.Gender;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUid(String uid);
    Optional<User> findByUidAndIsDeletedIsFalse(String uid);
    List<User> findAllByGender(Gender gender);
}
