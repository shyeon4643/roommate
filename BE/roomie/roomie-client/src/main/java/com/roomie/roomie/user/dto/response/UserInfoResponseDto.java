package com.roomie.roomie.user.dto.response;

import com.roommate.roommate.post.domain.PostArea;
import com.roommate.roommate.post.domain.PostCategory;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.domain.enums.Gender;
import com.roommate.roommate.user.domain.enums.LifeCycle;
import com.roommate.roommate.user.domain.enums.Pet;
import com.roommate.roommate.user.domain.enums.Smoking;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "User 기본 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserInfoResponseDto {

    private Long userId;
    private String name;
    private String uid;
    private String email;
    private String nickname;
    private LifeCycle lifeCycle;
    private Smoking smoking;
    private Gender gender;
    private Pet pet;
    private Integer fee;
    private String wishRoommate;
    private PostCategory category;
    private PostArea area;

    public UserInfoResponseDto(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.lifeCycle = user.getDetailRoommate().getLifeCycle();
        this.category = user.getDetailRoommate().getCategory();
        this.area = user.getDetailRoommate().getArea();
        this.fee = user.getDetailRoommate().getFee();
        this.gender = user.getDetailRoommate().getRoommateGender();
        this.pet = user.getDetailRoommate().getPet();
        this.smoking = user.getDetailRoommate().getSmoking();
        this.wishRoommate = user.getDetailRoommate().getWishRoommate();
    }


}
