package com.roommate.roommate.user.dto.request;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SignUpRequestDto {
    private String uid;
    private String password;
    private Date birth;
    private String email;
    private String name;
    private String nickname;
    private String phoneNum;
    private String mbti;

}
