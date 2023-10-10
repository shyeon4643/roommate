package com.roommate.roommate.post.domain;

import com.roommate.roommate.post.dto.request.CreatePostRequestDto;
import com.roommate.roommate.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.roomate.roomate.common.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;
    private String title;
    private String body;
    @Enumerated(EnumType.STRING)
    private PostArea area;
    @Enumerated(EnumType.STRING)
    private PostCategory category;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private int likeCount;
    private int viewCount;
    private int fee;

    @OneToMany(mappedBy = "post")
    private List<LikedPhoto> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostPhoto> postPhotos = new ArrayList<>();

    public void update(CreatePostRequestDto createPostRequestDto){
        this.title= createPostRequestDto.getTitle();
        this.body= createPostRequestDto.getBody();
        this.area=PostArea.valueOf(createPostRequestDto.getArea());
        this.category=PostCategory.valueOf(createPostRequestDto.getCategory());
    }

    public void delete(){
        this.setIsDeleted(true);
    }

    @Builder
    public Post(String title, String body, int fee, PostCategory category, PostArea area, User user){
        this.title=title;
        this.body=body;
        this.category=category;
        this.area=area;
        this.fee=fee;
        this.user=user;
        this.setIsDeleted(false);
    }

    public void savedLikeCount(boolean like){
        if(like==false) {
            this.likeCount++;
        }else if(like==true){
            this.likeCount--;
        }else if(this.likeCount<=0){
            this.likeCount=0;

        }
    }


    public List<Comment> getComments(){
        int size = this.comments.size();
        if(size<1) return null;
        List<Comment> comments = new ArrayList<>(this.comments);
        comments.removeIf(comment -> comment.getIsDeleted()==true);
        Collections.reverse(comments);
        return comments;
    }
}
