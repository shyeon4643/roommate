package com.roommate.roommate.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountTokenInfoDto {

    private String token;


    public AccountTokenInfoDto(String token){
        this.token = token;
    }
}
