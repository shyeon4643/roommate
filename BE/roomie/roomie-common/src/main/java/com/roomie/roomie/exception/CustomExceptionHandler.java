package com.roomie.roomie.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<DefaultExceptionResponseDto> handleCustomException(CustomException e) {

        return DefaultExceptionResponseDto.exceptionResponse(e.getExceptionCode());
    }
}
