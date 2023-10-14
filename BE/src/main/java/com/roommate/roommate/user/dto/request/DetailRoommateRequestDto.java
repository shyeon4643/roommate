package com.roommate.roommate.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DetailRoommateRequestDto {
    private String lifeCycle;
    private String smoking;
    private String gender;
    private String pet;
    private Integer fee;
    private String wishRoommate;
    private String category;
    private String area;
}
