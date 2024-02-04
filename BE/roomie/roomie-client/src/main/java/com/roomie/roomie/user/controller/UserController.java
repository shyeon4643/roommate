package com.roomie.roomie.user.controller;


import com.example.roomie.domain.user.User;
import com.example.roomie.domain.user.service.UserService;
import com.roomie.roomie.common.DefaultResponseDto;
import com.roomie.roomie.security.JwtTokenProvider;
import com.roomie.roomie.user.dto.request.DetailRoommateRequestDto;
import com.roomie.roomie.user.dto.request.SignInRequestDto;
import com.roomie.roomie.user.dto.request.SignUpRequestDto;
import com.roomie.roomie.user.dto.request.UpdateUserRequestDto;
import com.roomie.roomie.user.dto.response.UserInfoResponseDto;
import com.roomie.roomie.user.dto.response.UserLoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "회원", description = "회원 API")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원 가입")
    @PostMapping("/join")
    public ResponseEntity<DefaultResponseDto> join(@RequestBody @Valid SignUpRequestDto signUpRequesetDto) {

        userService.join(signUpRequesetDto);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_REGISTERED")
                        .responseMessage("회원 가입 완료")
                        .data(null)
                        .build());
    }

    @Operation(summary = "회원 로그인")
    @PostMapping("/login")
    public ResponseEntity<DefaultResponseDto> login(@RequestBody @Valid SignInRequestDto signInRequesetDto) {

        UserLoginResponseDto response = userService.login(signInRequesetDto);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_LOGIN")
                        .responseMessage("회원 로그인 완료")
                        .data(response)
                        .build());
    }

    @Operation(summary = "원하는 룸메이트 작성")
    @PostMapping("/detailRoommate")
    public ResponseEntity<DefaultResponseDto> writeDetailRoommate(
            @RequestBody @Valid DetailRoommateRequestDto detailRoommateRequestDto,
            HttpServletRequest servletRequest) {

        Long uid = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        UserInfoResponseDto response = userService.writeDetailRoommate(detailRoommateRequestDto, uid);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("DETAIL_ROOMMATE_REGISTER")
                        .responseMessage("원하는 룸메이트 정보 등록 완료")
                        .data(response)
                        .build());
    }

    @Operation(summary = "원하는 룸메이트 정보 변경")
    @PatchMapping("/detailRoommate")
    public ResponseEntity<DefaultResponseDto<Object>> updateDetailRoommate(
            HttpServletRequest servletRequest,
            @RequestBody @Valid DetailRoommateRequestDto detailRoommateRequestDto
    ) {

        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        UserInfoResponseDto response = userService.updateDetailRoommate(detailRoommateRequestDto, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("USER 정보 변경 완료")
                        .data(response)
                        .build());
    }

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/mypage")
    public ResponseEntity<DefaultResponseDto<Object>> myPage(
            HttpServletRequest servletRequest
    ) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        UserInfoResponseDto response = userService.myPage(id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_FOUND")
                        .responseMessage("User 단건 조회 완료")
                        .data(response)
                        .build());
    }

    @Operation(summary = "회원 단건 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<DefaultResponseDto<Object>> findOneUser(
            @PathVariable("userId") Long userId
    ) {

        User user = userService.findById(userId);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_FOUND")
                        .responseMessage("User 단건 조회 완료")
                        .data(response)
                        .build());
    }

    @Operation(summary = "회원 정보 변경")
    @PatchMapping("/user")
    public ResponseEntity<DefaultResponseDto<Object>> updateUser(
            HttpServletRequest servletRequest,
            @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto
    ) {


        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        UserInfoResponseDto response = userService.updateUserInformation(id, updateUserRequestDto.getEmail(),
                updateUserRequestDto.getPassword(), updateUserRequestDto.getNickname());

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("USER 정보 변경 완료")
                        .data(response)
                        .build());
    }


    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/user")
    public ResponseEntity<DefaultResponseDto<Object>> deleteUser(
            HttpServletRequest servletRequest
    ) {

        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        userService.deleteUser(id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("USER 삭제 완료")
                        .data(null)
                        .build());
    }

}
