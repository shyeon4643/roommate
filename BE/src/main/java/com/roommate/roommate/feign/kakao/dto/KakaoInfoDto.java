package com.roommate.roommate.feign.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoInfoDto {
    private String accessToken;
    private String refreshToken;

    @Getter
    @NoArgsConstructor
    public static class KakaoUserIdInfoDto {
        private Long id;
    }
}
