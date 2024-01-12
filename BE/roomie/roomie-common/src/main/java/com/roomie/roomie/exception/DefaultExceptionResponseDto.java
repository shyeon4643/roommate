package com.roomie.roomie.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultExceptionResponseDto {

    private String responseCode;

    private String responseMessage;

    public static ResponseEntity<DefaultExceptionResponseDto> exceptionResponse(final ExceptionCode exceptionCode) {
        return ResponseEntity
                .status(exceptionCode.getHttpStatus())
                .body(DefaultExceptionResponseDto.builder()
                        .responseCode(exceptionCode.name())
                        .responseMessage(exceptionCode.getMessage())
                        .build()
                );
    }
}
