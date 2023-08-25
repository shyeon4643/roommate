package com.roommate.roommate.user.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.dto.request.CreateDetailRoommateRequestDto;
import com.roommate.roommate.user.dto.request.SignInRequestDto;
import com.roommate.roommate.user.dto.request.SignUpRequestDto;
import com.roommate.roommate.user.dto.request.UpdateUserRequestDto;
import com.roommate.roommate.user.dto.response.UserInfoResponseDto;
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
        log.info("[Join] 회원 가입 정보 전달");
        if(userService.findByUid(signUpRequesetDto.getUid())==null){
        User user = userService.join(signUpRequesetDto);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_REGISTERED")
                        .responseMessage("회원 가입 완료")
                        .data(response)
                        .build());
        }else{
            return ResponseEntity.status(404)
                    .body(DefaultResponseDto.builder()
                            .responseCode("DENIED_ID")
                            .responseMessage("이미 사용중인 아이디입니다.")
                            .data(signUpRequesetDto.getUid())
                            .build());
        }
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
    public ResponseEntity<DefaultResponseDto> login(@RequestBody @Valid SignInRequestDto signInRequesetDto){
        log.info("[login] 회원 로그인 정보 전달");

        User user = userService.login(signInRequesetDto);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(201)
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
    public ResponseEntity<DefaultResponseDto> detailRoommate(
            @RequestBody @Valid CreateDetailRoommateRequestDto createDetailRoommateRequestDto,
            HttpServletRequest servletRequest){

        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        User user = userService.detailRoommate(createDetailRoommateRequestDto, uid);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("DETAIL_ROOMMATE_REGISTER")
                        .responseMessage("원하는 룸메이트 정보 등록 완료")
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
    @GetMapping("/user")
    public ResponseEntity<DefaultResponseDto<Object>> findOneUser(
            HttpServletRequest servletRequest
    ) {
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));

        User user = userService.findByUid(uid);

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
    @PutMapping("/user")
    public ResponseEntity<DefaultResponseDto<Object>> updateUser(
            HttpServletRequest servletRequest,
            @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto
    ) {


        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        User user = userService.findByUid(uid);

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


        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        User user = userService.findByUid(uid);

        userService.deleteUser(user);

        UserInfoResponseDto response = new UserInfoResponseDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("USER 정보 변경 완료")
                        .data(response)
                        .build());
    }

}
