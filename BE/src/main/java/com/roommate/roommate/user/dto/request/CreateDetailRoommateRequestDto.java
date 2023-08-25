package com.roommate.roommate.user.dto.request;

import com.roommate.roommate.post.domain.PostArea;
import com.roommate.roommate.post.domain.PostCategory;
import com.roommate.roommate.user.domain.enums.Gender;
import com.roommate.roommate.user.domain.enums.LifeCycle;
import com.roommate.roommate.user.domain.enums.Pet;
import com.roommate.roommate.user.domain.enums.Smoking;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateDetailRoommateRequestDto {
    private String lifeCycle;
    private String smoking;
    private String gender;
    private String pet;
    private Integer fee;
    private String wishRoommate;
    private String category;
    private String area;
}
