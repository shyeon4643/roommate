package com.example.roomie.domain.post.enums;

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

