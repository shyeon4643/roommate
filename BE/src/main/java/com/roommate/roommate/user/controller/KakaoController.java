package com.roommate.roommate.user.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.feign.kakao.dto.KakaoInfoDto;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.dto.response.AccountTokenInfoDto;
import com.roommate.roommate.user.dto.response.UserLoginResponseDto;
import com.roommate.roommate.user.service.KakaoService;
import com.roommate.roommate.user.dto.request.SignUpRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kakao")
public class KakaoController {

    private final KakaoService kakaoService;


    @ApiOperation(value="로그인")
    @PostMapping("/loginOrsignUp")
    public ResponseEntity<DefaultResponseDto<Object>> getToken(@RequestParam("code") String code,
                                                               @RequestBody SignUpRequestDto signUpRequestDto){

        User user = kakaoService.loginOrJoin(code, signUpRequestDto);

        UserLoginResponseDto response = new UserLoginResponseDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("KAKAO_LOGIN")
                        .responseMessage("KAKAO 로그인")
                        .data(response)
                        .build());
    }


}
