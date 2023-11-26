package com.roommate.roommate.feign.kakao;

import com.roommate.roommate.feign.kakao.dto.KakaoInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name ="RequestKakaoUserInfoClient",
        url = "https://kapi.kakao.com"  // 변경된 URL로 수정
)
public interface RequestKakaoUserInfoClient {
    @GetMapping("/v1/user/access_token_info")
    KakaoInfoDto.KakaoUserIdInfoDto getUserInfo(
            @RequestHeader("Authorization") String authorization
    );
}
