package com.roommate.roommate.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostArea {
    강남구("강남구"), 강동구("강동구"), 강북구("강북구"), 강서구("강서구"), 관악구("관악구"),
    구로구("구로구"), 금천구("금천구"), 노원구("노원구"), 도봉구("도봉구"), 동대문구("동대문구"),
    동작구("동작구"), 마포구("마포구"), 서대문구("서대문구"), 서초구("서초구"), 성동구("성동구"),
    송파구("송파구"), 양천구("양천구"), 영등포구("영등포구"), 용산구("용산구"), 은평구("은평구"),
    종로구("종로구"), 중구("중구"), 중랑구("중랑구"), None("None");

    private String value;

}

