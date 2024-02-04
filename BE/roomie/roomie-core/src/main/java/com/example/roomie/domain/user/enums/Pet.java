package com.example.roomie.domain.user.enums;

import lombok.Getter;

@Getter
public enum Pet {
    좋아요,
    싫어요,
    상관없어요,
    None;

    private String value;
}
