package com.roomie.roomie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /**
     * 400 BAD_REQUEST : 잘못된 요청
     */
    FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "입력은 필수 입니다."),
    INCORRECT_PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호가 불일치 합니다."),
    INCORRECT_ID_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호가 불일치 합니다."),

    // 형식
    EMAIL_CHARACTER_INVALID(HttpStatus.BAD_REQUEST, "올바른 형식의 이메일이 아닙니다."),
    PASSWORD_CHARACTER_INVALID(HttpStatus.BAD_REQUEST, "올바른 형식의 비밀번호가 아닙니다."),

    //JWT
    ROLE_CHARACTER_INVALID(HttpStatus.BAD_REQUEST, "올바른 권한이 아닙니다."),
    EXPRIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT입니다."),
    INCORRECT_TOKEN(HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다."),

    JWT_CHARACTER_INVALID(HttpStatus.BAD_REQUEST, "올바른 형식의 JWT 서명이 아닙니다."),
    // 사이즈
    PASSWORD_LENGTH_INVALID(HttpStatus.BAD_REQUEST, "비밀번호는 8~15자 이내여야 합니다."),

    /**
     * 401 UNAUTHORIZED : 인증되지 않은 사용자
     */
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),


    /**
     * 403 FORBIDDEN : 권한이 없는 사용자
     */
    FORBIDDEN_AUTHORIZATION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    FORBIDDEN_TARGET_ADMIN(HttpStatus.FORBIDDEN, "관리자는 대상이 될 수 없습니다."),

    /**
     * 404 NOT_FOUND : Resource 를 찾을 수 없음
     */
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 이메일이 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 사용자가 없습니다."),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 투두가 없습니다."),

    /**
     * 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
     */
    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 등록된 아이디 입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 등록된 이메일 입니다."),
    DUPLICATE_POST(HttpStatus.CONFLICT, "이미 작성한 게시글이 있습니다."),

    /**
     * 500 SERVER_ERROR : 서버 에러
     */
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
