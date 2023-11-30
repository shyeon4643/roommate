package com.roommate.roommate.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roomate.roomate.common.BaseEntity;
import com.roommate.roommate.post.domain.Comment;
import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.domain.LikedPost;
import com.roommate.roommate.user.domain.enums.Gender;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@BatchSize(size = 100)
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String token;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private DetailRoommate detailRoommate;

    private Long kakaoId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @BatchSize(size = 1000)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @BatchSize(size = 1000)
    private List<LikedPost> likes = new ArrayList<>();
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
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
    @Override
    public Collection<?extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new SimpleGrantedAuthority(Role.convertEnum(this.getRole())));
        return collectors;
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
        this.setUpdatedAt(null);
        this.setDetailRoommate(null);
        this.setIsDeleted(true);
    }

    // 계정 리턴
    @Override
    public String getUsername(){
        return this.email;
    }

    // 계정이 만료됐는지 리턴 (true : 만료안됨)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    //계정이 잠겼는지 리턴 (true : 잠기지 않음)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    //비밀번호가 만료됐는지 리턴 (true : 만료안됨)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    //계정이 활성화(사용가능) 되어있는지 리턴 (true : 활성화)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled(){
        return true;
    }

}



