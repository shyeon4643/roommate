package com.roommate.roommate.post.domain;

import com.roommate.roommate.user.domain.User;
import lombok.*;

import javax.persistence.*;

import com.roomate.roomate.common.BaseEntity;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String body;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Comment(String body, User user, Post post){
        this.body=body;
        this.user=user;
        this.post=post;
        this.setIsDeleted(false);
    }

    public void update(String body){
        this.body=body;
    }

    public void delete(){
        this.setIsDeleted(true);
    }
}
