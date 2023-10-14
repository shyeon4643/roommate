package com.roommate.roommate.user.dto.response;


import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "User 토큰 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenResponseDto {
    private String token;

    public UserTokenResponseDto(String token){
        this.token=token;
    }

}
