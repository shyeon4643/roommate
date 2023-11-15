package com.roommate.roommate.post.service;

import com.roommate.roommate.post.domain.*;
import com.roommate.roommate.post.dto.request.CreatePostRequestDto;
import com.roommate.roommate.post.dto.request.SearchPostDto;
import com.roommate.roommate.post.repository.PostLikedRepository;
import com.roommate.roommate.post.repository.PostPhotoRepository;
import com.roommate.roommate.post.repository.PostRepository;
import com.roommate.roommate.user.domain.Mbti;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.repository.MbtiRepository;
import com.roommate.roommate.user.repository.UserRepository;
import com.roommate.roommate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final MbtiRepository mbtiRepository;

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
    public Post findUserPost(User user){
        try{
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

    public List<Post> findAllLikedPostsByUser(String uid){
        List<Post> posts = new ArrayList<>();
        try {
            User user= userService.findByUid(uid);
            List<LikedPost> postLikes = postLikedRepository.findByUserIdAndIsDeletedIsFalse(user.getId());
            for(LikedPost likedPost : postLikes){
                posts.add(likedPost.getPost());
            }

            return posts;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    public LikedPost isLike(User user, Post post){
        try{
            return postLikedRepository.findByUserIdAndPostId(user.getId(), post.getId());
        }catch(RuntimeException e){
            throw new CustomException(SERVER_ERROR);
        }
    }

    @Transactional
    public LikedPost saveLike(Long postId, String uid) {
        try {
            User user = userService.findByUid(uid);
            Post post = postRepository.findById(postId).get();
            if(postLikedRepository.findByUserIdAndPostId(user.getId(),postId)==null) {
                LikedPost likedPost = LikedPost.builder()
                        .post(post)
                        .user(user)
                        .build();

                postLikedRepository.save(likedPost);
                return likedPost;
            }else{
                LikedPost likedPost = postLikedRepository.findByUserIdAndPostId(user.getId(), post.getId());
                likedPost.save();
                return likedPost;
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }

    }

    @Transactional
    public LikedPost deletedLike(Long likeId, String uid) {
        try {
            User user = userService.findByUid(uid);
            LikedPost likedPost = postLikedRepository.findByIdAndUserId(likeId,user.getId());
            likedPost.delete();

            return likedPost;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }

    }

    @Transactional
    public void savePhoto(Post post, List<MultipartFile> files) throws Exception {
        if (files.size() != 0) {
            String projectPath = System.getProperty("user.dir") + "\\\\src\\\\main\\\\resources\\\\static\\\\photos\\\\postPhoto";
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
    public Page<Post> searchPost(SearchPostDto searchPostDto, Pageable pageable)
    {
        int page = (pageable.getPageNumber()==0)?0:(pageable.getPageNumber()-1);
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.findByTitleContainingOrAreaContaining(searchPostDto.getKeyword(),PostArea.valueOf(searchPostDto.getKeyword()),pageRequest);

    }

    public List<Post> mbtiPosts(User user) {
        List<User> sameGender = userRepository.findAllByGender(user.getGender()); // 성별 필터
        Mbti mbti = mbtiRepository.findByMbti(user.getMbti());
        List<Post> mbtiPosts = new ArrayList<>();
        for (User sameGenderUser : sameGender) {
            if (sameGenderUser.getMbti().equals(mbti.getFirstMbti()) ||
                    sameGenderUser.getMbti().equals(mbti.getSecondMbti()) || sameGenderUser.getMbti()
                    .equals(mbti.getThirdMbti()) || sameGenderUser.getMbti().equals(mbti.getFourthMbti())) {
                for(int i=0;i<sameGenderUser.getPosts().size();i++){
                    if(sameGenderUser.getPosts().get(i).getIsDeleted()==false) {
                        mbtiPosts.add(sameGenderUser.getPosts().get(i));
                    }
                }
            }
        }
        return mbtiPosts;
    }
}
