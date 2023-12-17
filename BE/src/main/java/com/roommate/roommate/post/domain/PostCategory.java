package com.roommate.roommate.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum PostCategory {
    charter("charter"),
    monthly("monthly"),
    None("None");

    private final String value;

}

