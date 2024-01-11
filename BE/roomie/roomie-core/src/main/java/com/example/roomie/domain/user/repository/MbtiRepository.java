package com.example.roomie.domain.user.repository;

import com.roommate.roommate.user.domain.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Mbti findByMbti(String mbti);
}
