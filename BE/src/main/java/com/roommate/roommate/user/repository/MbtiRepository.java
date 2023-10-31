package com.roommate.roommate.user.repository;

import com.roommate.roommate.user.domain.Mbti;
import com.roommate.roommate.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Mbti findByMbti(String mbti);
}
