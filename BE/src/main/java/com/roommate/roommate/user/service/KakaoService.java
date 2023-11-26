package com.roommate.roommate.user.service;

import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.feign.kakao.RequestKakaoClient;
import com.roommate.roommate.feign.kakao.RequestKakaoUserInfoClient;
import com.roommate.roommate.feign.kakao.dto.KakaoInfoDto;
import com.roommate.roommate.feign.properties.KakaoProperties;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.dto.request.SignUpRequestDto;
import com.roommate.roommate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final RequestKakaoClient requestKakaoClient;
    private final RequestKakaoUserInfoClient requestKakaoUserInfoClient;
    private final KakaoProperties kakaoProperties;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    public User loginOrJoin(String code, SignUpRequestDto request){
        KakaoInfoDto kakaoInfoDto = requestKakaoClient.getToken(
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUri(),
                code,
                kakaoProperties.getClientSecret()
        );

        KakaoInfoDto.KakaoUserIdInfoDto kakaoUserInfo = requestKakaoUserInfoClient.getUserInfo("Bearer "+
                kakaoInfoDto.getAccessToken());

        User user = userRepository.findByKakaoId(kakaoUserInfo.getId()).orElse(null);

        if(user == null){
            user = userService.join(request);
        }
        user.setKakaoId(kakaoUserInfo.getId());

        userService.createToken(user);

        userRepository.save(user);
        return user;

    }



}
