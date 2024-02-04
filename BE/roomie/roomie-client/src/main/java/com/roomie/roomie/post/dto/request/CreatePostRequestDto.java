package com.roomie.roomie.post.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CreatePostRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    @NotBlank
    private String category;
    @NotBlank
    private String area;
    @NotBlank
    private int fee;

    private List<MultipartFile> files = new ArrayList<>();


}
