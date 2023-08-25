package com.roommate.roommate.post.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CreatePostRequestDto {

    private String title;
    private String body;
    private String category;
    private String area;
    private int fee;

    private List<MultipartFile> files = new ArrayList<>();


}
