package com.roommate.roommate.user.dto.response;

import com.roommate.roommate.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "UserLogin 기본 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLoginResponseDto {
    private String token;
    private boolean isInfo;

    public UserLoginResponseDto(User user) {
        this.token = user.getToken();
        if (user.getDetailRoommate() != null) {
            this.isInfo = true;
        } else {
            this.isInfo = false;
        }
    }
}
