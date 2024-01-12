package com.example.roomie.domain.user;

import com.example.roomie.common.BaseEntity;
import com.example.roomie.domain.post.Comment;
import com.example.roomie.domain.post.LikedPost;
import com.example.roomie.domain.post.Post;
import com.example.roomie.domain.user.enums.Gender;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.*;

@Entity
@BatchSize(size = 100)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    private String uid;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    private Date birth;
    private String mbti;
    private String nickname;
    private Character role;
    @Setter
    private String token;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Setter
    @Embedded
    private DetailRoommate detailRoommate;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    @BatchSize(size = 1000)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @BatchSize(size = 1000)
    private List<LikedPost> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = false)
    @BatchSize(size = 1000)
    private List<Comment> comments = new ArrayList<>();

    public List<LikedPost> getLikedPosts() {
        int size = this.likes.size();
        if (size < 1)
            return null;
        List<LikedPost> likedPosts = new ArrayList<>(this.likes);
        likedPosts.removeIf(likedPost -> likedPost.getIsDeleted().equals(true));
        Collections.reverse(likedPosts);
        return likedPosts;
    }

    @Builder
    public User(String uid,
                String password,
                String name,
                String phoneNum,
                String email,
                Date birth,
                String mbti,
                String nickname,
                Gender gender){
        this.uid=uid;
        this.password=password;
        this.name=name;
        this.phoneNum=phoneNum;
        this.email=email;
        this.birth=birth;
        this.nickname=nickname;
        this.mbti=mbti;
        this.role='U';
        this.gender=gender;
        this.setDetailRoommate(null);
        this.setIsDeleted(false);
    }

    public void updatePassword(String password){
        this.password=password;
    }

    public void updateNickname(String nickname){
        this.nickname=nickname;
    }

    public void updateEmail(String email){
        this.email=email;
    }

    public void delete(){
        this.uid=null;
        this.nickname=null;
        this.email=null;
        this.birth=null;
        this.phoneNum=null;
        this.mbti=null;
        this.role=null;
        this.password=null;
        this.token=null;
        this.name=null;
        this.detailRoommate=null;
        this.setUpdatedAt(null);
        this.setIsDeleted(true);
    }

    @Override
    public Collection<?extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new SimpleGrantedAuthority(Role.convertEnum(this.getRole())));
        return collectors;
    }


    @Override
    public String getUsername(){
        return this.email;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled(){
        return true;
    }

}



