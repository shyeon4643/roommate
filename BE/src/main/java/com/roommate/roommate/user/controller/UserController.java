package com.roommate.roommate.user.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.dto.request.DetailRoommateRequestDto;
import com.roommate.roommate.user.dto.request.SignInRequestDto;
import com.roommate.roommate.user.dto.request.SignUpRequestDto;
import com.roommate.roommate.user.dto.request.UpdateUserRequestDto;
import com.roommate.roommate.user.dto.response.AccountTokenInfoDto;
import com.roommate.roommate.user.dto.response.UserInfoResponseDto;
import com.roommate.roommate.user.dto.response.UserLoginResponseDto;
import com.roommate.roommate.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "회원")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 가입")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "USER_FOUND",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_USER"),
            @ApiResponse(responseCode = "404",
                    description = "DENIED_ID"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PostMapping("/join")
    public ResponseEntity<DefaultResponseDto> join(@RequestBody @Valid SignUpRequestDto signUpRequesetDto){
        userService.join(signUpRequesetDto);


        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_REGISTERED")
                        .responseMessage("회원 가입 완료")
                        .build());
    }

    @ApiOperation(value = "회원 로그인")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "USER_LOGIN",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_USER"),
            @ApiResponse(responseCode = "404",
                    description = "USER_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })

    @PostMapping("/login")
    public ResponseEntity<DefaultResponseDto> login(@RequestBody @Valid SignInRequestDto signInRequesetDto) {

        User user = userService.login(signInRequesetDto);
        AccountTokenInfoDto accountTokenInfoDto = userService.createToken(user);
        UserLoginResponseDto response = new UserLoginResponseDto(user, accountTokenInfoDto);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_LOGIN")
                        .responseMessage("회원 로그인 완료")
                        .data(response)
                        .build());
    }
    @ApiOperation(value = "원하는 룸메이트 작성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "DETAIL_ROOMMATE",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_USER"),
            @ApiResponse(responseCode = "404",
                    description = "USER_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })

    @PostMapping("/detailRoommate")
    public ResponseEntity<DefaultResponseDto> writeDetailRoommate(
            @RequestBody @Valid DetailRoommateRequestDto detailRoommateRequestDto,
            HttpServletRequest servletRequest){

        Long uid = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.detailRoommate(detailRoommateRequestDto, uid);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("DETAIL_ROOMMATE_REGISTER")
                        .responseMessage("원하는 룸메이트 정보 등록 완료")
                        .data(response)
                        .build());
    }

    @ApiOperation(value = "원하는 룸메이트 정보 변경")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "DETAIL_ROOMMATE",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_USER"),
            @ApiResponse(responseCode = "404",
                    description = "USER_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PatchMapping("/detailRoommate")
    public ResponseEntity<DefaultResponseDto<Object>> updateDetailRoommate(
            HttpServletRequest servletRequest,
            @RequestBody @Valid DetailRoommateRequestDto detailRoommateRequestDto
    ) {


        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.updateDetailRoommate(detailRoommateRequestDto, id);



        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("USER 정보 변경 완료")
                        .data(response)
                        .build());
    }

    @ApiOperation(value = "마이페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "USER_FOUND",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_USER"),
            @ApiResponse(responseCode = "404",
                    description = "USER_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/mypage")
    public ResponseEntity<DefaultResponseDto<Object>> myPage(
            HttpServletRequest servletRequest
    ) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        User user = userService.findById(id);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_FOUND")
                        .responseMessage("User 단건 조회 완료")
                        .data(response)
                        .build());
    }

    @ApiOperation(value = "회원 단건 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "USER_FOUND",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_USER"),
            @ApiResponse(responseCode = "404",
                    description = "USER_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
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

    @ApiOperation(value = "회원 정보 변경")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "USER_UPDATED",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_USER"),
            @ApiResponse(responseCode = "404",
                    description = "USER_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PatchMapping("/user")
    public ResponseEntity<DefaultResponseDto<Object>> updateUser(
            HttpServletRequest servletRequest,
            @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto
    ) {


        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);

        userService.updatePassword(user, updateUserRequestDto.getPassword());
        userService.updateEmail(user, updateUserRequestDto.getEmail());
        userService.updateNickname(user, updateUserRequestDto.getNickname());

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("USER 정보 변경 완료")
                        .data(response)
                        .build());
    }


    @DeleteMapping("/user")
    public ResponseEntity<DefaultResponseDto<Object>> deleteUser(
            HttpServletRequest servletRequest
    ) {


        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);

        userService.deleteUser(user);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("USER 삭제 완료")
                        .data(response)
                        .build());
    }

}
