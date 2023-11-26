package com.roommate.roommate.post.domain;

import lombok.Getter;


@Getter
public enum PostCategory {
    charter("charter"),
    monthly("monthly"),
    None("None");

    private final String value;

    PostCategory(String value) {
        this.value = value;
    }
}
