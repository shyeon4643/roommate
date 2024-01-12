package com.example.roomie.domain.user.repository;


import com.example.roomie.domain.user.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Mbti findByMbti(String mbti);
}
