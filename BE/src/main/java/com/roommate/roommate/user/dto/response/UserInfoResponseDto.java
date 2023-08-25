package com.roommate.roommate.user.dto.response;

import com.roommate.roommate.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "Post 기본 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserInfoResponseDto {

    private Long userId;
    private String name;
    private String uid;
    private String email;
    private String nickname;

    public UserInfoResponseDto(User user){
        this.userId=user.getId();
        this.name=user.getName();
        this.uid=user.getUid();
        this.email=user.getEmail();
        this.nickname=user.getNickname();
    }

}
