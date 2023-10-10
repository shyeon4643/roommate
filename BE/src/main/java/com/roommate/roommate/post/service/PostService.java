package com.roommate.roommate.post.service;

import com.roommate.roommate.post.domain.*;
import com.roommate.roommate.post.dto.request.CreatePostRequestDto;
import com.roommate.roommate.post.repository.PostLikedRepository;
import com.roommate.roommate.post.repository.PostPhotoRepository;
import com.roommate.roommate.post.repository.PostRepository;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.repository.UserRepository;
import com.roommate.roommate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.roommate.roommate.exception.CustomException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.roommate.roommate.exception.ExceptionCode.SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PostRepository postRepository;
    private final PostLikedRepository postLikedRepository;
    private final PostPhotoRepository postPhotoRepository;

    /*
     * 게시글 등록
     * 500(SERVER_ERROR)
     * */
    @Transactional
    public Post savePost(CreatePostRequestDto createPostRequestDto, String uid) throws Exception {
        try {
            User user =  userService.findByUid(uid);

            Post post = Post.builder()
                    .title(createPostRequestDto.getTitle())
                    .body(createPostRequestDto.getBody())
                    .area(PostArea.valueOf(createPostRequestDto.getArea()))
                    .category(PostCategory.valueOf(createPostRequestDto.getCategory()))
                    .fee(createPostRequestDto.getFee())
                    .user(user)
                    .build();
            if(createPostRequestDto.getFiles()!=null) {
                savePhoto(post, createPostRequestDto.getFiles());
            }
            postRepository.save(post);
            return post;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }

    }
    /*
     * 카테고리별 게시글 조회
     * 500(SERVER_ERROR)
     * */
    public List<Post> findAllPostsByCategory(String category){
        try{
            List<Post> posts = postRepository.findByCategoryAndIsDeletedIsFalse(PostCategory.valueOf(category));

            return posts;
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 지역별 게시글 조회
     * 500(SERVER_ERROR)
     * */
    public List<Post> findAllPostsByArea(String area){
        try{
            List<Post> posts = postRepository.findByAreaAndIsDeletedIsFalse(PostArea.valueOf(area));

            return posts;
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 유저가 쓴 게시글 조회
     * 500(SERVER_ERROR)
     * */
    public Post findPostByUid(String uid){
        try{
            User user = userRepository.findByUid(uid);
            Post post = postRepository.findByUserIdAndIsDeletedIsFalse(user.getId());

            return post;
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 게시글 단건 조회
     * 500(SERVER_ERROR)
     * */
    public Post findOnePost(Long postId){
        try{
            Post post = postRepository.findById(postId).get();

            return post;
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 게시글 수정
     * 500(SERVER_ERROR)
     * */
    @Transactional
    public Post updatePost(Long postId, CreatePostRequestDto createPostRequestDto, String uid){
        try{
            User user = userService.findByUid(uid);
            Post post = postRepository.findByIdAndUserId(postId, user.getId());
            post.update(createPostRequestDto);

            return post;
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 게시글 삭제
     * 500(SERVER_ERROR)
     * */
    @Transactional
    public Post deletePost(Long postId, String uid){
        try{
            User user = userService.findByUid(uid);
            Post post = postRepository.findByIdAndUserId(postId, user.getId());
            post.delete();

            return post;
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    public List<LikedPhoto> findAllLikedPostsByUser(String uid){
        List<Post> posts = new ArrayList<>();
        try {
            User user= userService.findByUid(uid);
            List<LikedPhoto> postLikes = postLikedRepository.findByUserIdAndIsDeletedIsFalse(user.getId());


            return postLikes;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    @Transactional
    public LikedPhoto saveLike(Long postId, String uid) {
        try {
            User user = userService.findByUid(uid);
            Post post = postRepository.findById(postId).get();
            LikedPhoto likedPhoto = LikedPhoto.builder()
                    .post(post)
                    .user(user)
                    .build();

            postLikedRepository.save(likedPhoto);
            return likedPhoto;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }

    }

    @Transactional
    public LikedPhoto deletedLike(Long likeId, String uid) {
        try {
            User user = userService.findByUid(uid);
            LikedPhoto likedPhoto = postLikedRepository.findByIdAndUserId(likeId,user.getId());
            likedPhoto.delete();

            return likedPhoto;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }

    }

    @Transactional
    public void savePhoto(Post post, List<MultipartFile> files) throws Exception {
        if (files.size() != 0) {
            String projectPath = System.getProperty("user.dir") + "\\\\BE\\\\src\\\\main\\\\resources\\\\static\\\\photos\\\\postPhoto";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String extension = filename.substring(filename.lastIndexOf("."));
                String saveFilename = UUID.randomUUID()+extension;

                File saveFile = new File(projectPath, saveFilename);
                file.transferTo(saveFile);

                PostPhoto postPhoto = PostPhoto.builder()
                        .photoUrl(saveFilename)
                        .post(post)
                        .build();
                postPhotoRepository.save(postPhoto);
            }

        }
    }



}
