package com.roommate.roommate.post.dto.response;

import com.roommate.roommate.post.domain.Post;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "Post 기본 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostInfoResponseDto {

    private Long postId;
    private String title;
    private String body;

    public PostInfoResponseDto(Post post){
        this.postId=post.getId();
        this.title=post.getTitle();
        this.body=post.getBody();
    }
}
