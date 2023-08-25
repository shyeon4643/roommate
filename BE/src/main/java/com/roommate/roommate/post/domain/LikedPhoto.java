package com.roommate.roommate.post.domain;

import com.roommate.roommate.user.domain.User;
import lombok.*;

import javax.persistence.*;

import com.roomate.roomate.common.BaseEntity;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikedPhoto extends BaseEntity {

    @Id
    @Column(name="like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;



    @Builder
    public LikedPhoto(Post post, User user){
        this.post=post;
        this.user=user;
        this.setIsDeleted(false);

        post.savedLikeCount(getIsDeleted());
        post.getLikes().add(this);
        user.getLikes().add(this);
    }


    public void delete(){
        this.setIsDeleted(true);
        post.savedLikeCount(getIsDeleted());}
}
