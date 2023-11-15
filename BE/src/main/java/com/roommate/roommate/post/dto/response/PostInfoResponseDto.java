package com.roommate.roommate.post.dto.response;

import com.roommate.roommate.post.domain.*;
import com.roommate.roommate.user.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Post 기본 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostInfoResponseDto {

    private Long postId;
    private String title;
    private String body;
    private PostArea area;
    private Integer fee;
    private List<PostPhoto> photos = new ArrayList<>();
    private List<CommentInfoResponseDto> comments = new ArrayList<>();
    private LocalDateTime updateAt;
    private PostCategory category;
    private String writer;
    private int viewCount;
    private int likeCount;
    private Long currentUser;
    private Long writerUser;
    private boolean isLike;
    private Long likedId;
    private List<String> path = new ArrayList<>();

    public PostInfoResponseDto(Post post, User user){
        this.postId=post.getId();
        this.title=post.getTitle();
        this.body=post.getBody();
        this.area=post.getArea();
        this.fee=post.getFee();
        if(post.getPostPhotos()!=null){
            List<PostPhoto> photos = post.getPostPhotos();
            for(PostPhoto photo : photos){
                this.photos.add(photo);
            }
        }
        if(post.getComments()!=null){
            List<Comment> comments = post.getComments();
            for(Comment comment : comments){
                this.comments.add(new CommentInfoResponseDto(comment));
            }
        }
        this.updateAt=post.getUpdatedAt();
        this.category=post.getCategory();
        this.writer=post.getUser().getNickname();
        this.viewCount=post.getViewCount();
        this.likeCount=post.getLikeCount();
        this.currentUser=user.getId();
        this.writerUser=post.getUser().getId();
        if(post.getPostPhotos()!=null){
            List<PostPhoto> postPhotos = post.getPostPhotos();
            for(PostPhoto postPhoto : postPhotos){
                this.path.add(postPhoto.getPhotoUrl());
            }
        }
    }

    public PostInfoResponseDto(Post post){
        this.postId=post.getId();
        this.title=post.getTitle();
        this.body=post.getBody();
        this.area=post.getArea();
        this.fee=post.getFee();
        if(post.getPostPhotos()!=null){
            List<PostPhoto> photos = post.getPostPhotos();
            for(PostPhoto photo : photos){
                this.photos.add(photo);
            }
        }
        if(post.getComments()!=null){
            List<Comment> comments = post.getComments();
            for(Comment comment : comments){
                this.comments.add(new CommentInfoResponseDto(comment));
            }
        }
        this.updateAt=post.getUpdatedAt();
        this.category=post.getCategory();
        this.writer=post.getUser().getNickname();
        this.viewCount=post.getViewCount();
        this.likeCount=post.getLikeCount();
        this.writerUser=post.getUser().getId();
        if(post.getPostPhotos()!=null){
            List<PostPhoto> postPhotos = post.getPostPhotos();
            for(PostPhoto postPhoto : postPhotos){
                this.path.add(postPhoto.getPhotoUrl());
            }
        }
    }

    public PostInfoResponseDto(Post post, User user, LikedPost likedPost){
        this.postId=post.getId();
        this.title=post.getTitle();
        this.body=post.getBody();
        this.area=post.getArea();
        this.fee=post.getFee();
        if(likedPost==null){
            this.isLike=true;
        }else{
            this.isLike=likedPost.getIsDeleted();
            this.likedId=likedPost.getId();
        }
        if(post.getPostPhotos()!=null){
            List<PostPhoto> photos = post.getPostPhotos();
            for(PostPhoto photo : photos){
                this.photos.add(photo);
            }
        }
        if(post.getComments()!=null){
            List<Comment> comments = post.getComments();
            for(Comment comment : comments){
                this.comments.add(new CommentInfoResponseDto(comment));
            }
        }
        this.updateAt=post.getUpdatedAt();
        this.category=post.getCategory();
        this.writer=post.getUser().getNickname();
        this.viewCount=post.getViewCount();
        this.likeCount=post.getLikeCount();
        this.currentUser=user.getId();
        this.writerUser=post.getUser().getId();
        if(post.getPostPhotos()!=null){
            List<PostPhoto> postPhotos = post.getPostPhotos();
            for(PostPhoto postPhoto : postPhotos){
                this.path.add(postPhoto.getPhotoUrl());
            }
        }
    }
}
