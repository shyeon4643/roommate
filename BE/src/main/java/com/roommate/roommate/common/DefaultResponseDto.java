package com.roommate.roommate.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@ApiModel(value = "기본 응답")
public class DefaultResponseDto<T> {

    @ApiModelProperty(position = 1, value = "응답 코드", example = "RESPONSE_CODE")
    private String responseCode;

    @ApiModelProperty(position = 2, value = "응답 메세지", example = "응답 메세지")
    private String responseMessage;

    @ApiModelProperty(position = 3, value = "데이터")
    private T data;

    public DefaultResponseDto(final String responseCode, final String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static <T> DefaultResponseDto<T> responseDto(final String responseCode, final String responseMessage) {
        return  response(responseCode, responseMessage, null);
    }

    public static <T> DefaultResponseDto<T> response(final String responseCode, final String responseMessage, final T data) {
        return DefaultResponseDto.<T>builder()
                .responseCode(responseCode)
                .responseMessage(responseMessage)
                .data(data)
                .build();
    }
}
