package com.roommate.roommate.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roomate.roomate.common.BaseEntity;
import com.roommate.roommate.post.domain.Comment;
import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.domain.LikedPhoto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
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

    @Embedded
    private DetailRoommate detailRoommate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikedPhoto> likes = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public List<LikedPhoto> getLikedPosts() {
        int size = this.likes.size();
        if (size < 1)
            return null;
        List<LikedPhoto> likedPosts = new ArrayList<>(this.likes);
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
                String nickname){
        this.uid=uid;
        this.password=password;
        this.name=name;
        this.phoneNum=phoneNum;
        this.email=email;
        this.birth=birth;
        this.nickname=nickname;
        this.role='U';
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



