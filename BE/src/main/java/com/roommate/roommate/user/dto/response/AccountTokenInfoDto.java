package com.roommate.roommate.user.dto.response;

import com.roommate.roommate.feign.kakao.dto.KakaoInfoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountTokenInfoDto {

    private String accessToken;
    private String refreshToken;

    public AccountTokenInfoDto(KakaoInfoDto kakaoInfoDto){
        this.accessToken = kakaoInfoDto.getAccessToken();
        this.refreshToken = kakaoInfoDto.getRefreshToken();
    }

    public AccountTokenInfoDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
