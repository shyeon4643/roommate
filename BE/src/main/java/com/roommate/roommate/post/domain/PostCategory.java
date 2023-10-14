package com.roommate.roommate.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public enum PostCategory {
    charter,
    monthly,
    None;

    private String value;
}
