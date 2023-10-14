package com.roommate.roommate.post.dto.response;

import com.roommate.roommate.post.domain.LikedPost;
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

    public LikedInfoResponseDto(LikedPost likedPost){
        this.likedId= likedPost.getId();
        this.postId= likedPost.getPost().getId();
        this.isDeleted= likedPost.getIsDeleted();
    }

    public LikedInfoResponseDto(LikedPost likedPhoto, User user){
        this.likedId= likedPhoto.getId();
        if(user.getLikedPosts()!=null){
            List<LikedPost> likedPosts = user.getLikedPosts();
            for(LikedPost likedPost : likedPosts){
                this.posts.add(new PostInfoResponseDto(likedPost.getPost(),user));
            }
        }
    }
}
