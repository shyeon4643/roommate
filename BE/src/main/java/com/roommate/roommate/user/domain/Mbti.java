package com.roommate.roommate.user.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
@AllArgsConstructor
public class Mbti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mbti_id")
    private Long id;

    String mbti;

    String firstMbti;

    String secondMbti;

    String thirdMbti;

    String fourthMbti;

}
