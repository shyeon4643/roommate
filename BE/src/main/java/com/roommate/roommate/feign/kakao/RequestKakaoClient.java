package com.roommate.roommate.feign.kakao;

import com.roommate.roommate.feign.config.FeignConfig;
import com.roommate.roommate.feign.kakao.dto.KakaoInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        name ="RequestKakaoClient",
        url = "https://kauth.kakao.com",
        configuration = FeignConfig.class
)
public interface RequestKakaoClient {
    @PostMapping("/oauth/token?grant_type=authorization_code")
    KakaoInfoDto getToken(
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("client_secret") String clientSecret
    );

 /*   @GetMapping("/v1/user/access_token_info")
    KakaoInfoDto.KakaoUserIdInfoDto getUserInfo(
            @RequestHeader("Authorization") String authorization
    );*/
}
