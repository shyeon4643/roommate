package com.roommate.roommate.post.dto.response;

import com.roommate.roommate.post.domain.LikedPhoto;
import com.roommate.roommate.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@ApiModel(value = "Liked 기본 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikedInfoResponseDto {

    private Long likedId;
    private Long postId;
    private boolean isDeleted;
    private List<PostInfoResponseDto> posts = new ArrayList<>();

    public LikedInfoResponseDto(LikedPhoto likedPhoto){
        this.likedId= likedPhoto.getId();
        this.postId= likedPhoto.getPost().getId();
        this.isDeleted= likedPhoto.getIsDeleted();
    }

    public LikedInfoResponseDto(LikedPhoto likedPhoto, User user){
        this.likedId= likedPhoto.getId();
        if(user.getLikedPosts()!=null){
            List<LikedPhoto> likedPosts = user.getLikedPosts();
            for(LikedPhoto likedPost : likedPosts){
                this.posts.add(new PostInfoResponseDto(likedPost.getPost()));
            }
        }
    }
}
