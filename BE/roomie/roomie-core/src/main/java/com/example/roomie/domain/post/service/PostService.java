package com.example.roomie.domain.post.service;

import com.roommate.roommate.common.PageResponse;
import com.roommate.roommate.common.SliceResponse;
import com.roommate.roommate.post.dto.request.CreatePostRequestDto;
import com.roommate.roommate.post.dto.response.LikedInfoResponseDto;
import com.roommate.roommate.post.dto.response.PostInfoResponseDto;
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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.roommate.roommate.exception.CustomException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.roommate.roommate.exception.ExceptionCode.DUPLICATE_POST;
import static com.roommate.roommate.exception.ExceptionCode.SERVER_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final MbtiRepository mbtiRepository;
    private final PostRepository postRepository;
    private final PostLikedRepository postLikedRepository;
    private final PostPhotoRepository postPhotoRepository;

    @Transactional
    public PostInfoResponseDto savePost(CreatePostRequestDto createPostRequestDto, Long id) throws Exception {
        try {
            User user = userService.findById(id);

            if(postRepository.findByUserIdAndIsDeletedIsFalse(id)!=null) {
                throw new CustomException(null, ExceptionCode.DUPLICATE_POST);
            }

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

            return new PostInfoResponseDto(post, user);

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }

    }

    public PageResponse<PostInfoResponseDto> findAllPostsByCategory(String category, Pageable pageable, Long id){
        try{
            Page<Post> posts = postRepository.findByCategoryAndIsDeletedIsFalse(category, pageable);

            User user = userService.findById(id);

            List<PostInfoResponseDto> postDtos = posts.getContent().stream()
                    .map(post -> new PostInfoResponseDto(post, user))
                    .collect(Collectors.toList());

            Page<PostInfoResponseDto> pageResponse = new PageImpl<>(postDtos, pageable, posts.getTotalElements());

            return new PageResponse(pageResponse);

        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }


    public PostInfoResponseDto findPostByUser(Long id){
        try{
            User user = userService.findById(id);

            Post post = postRepository.findByUserIdAndIsDeletedIsFalse(user.getId());


            return new PostInfoResponseDto(post, user);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    @Transactional
    public PostInfoResponseDto detailPost(Long id, Long postId){
        try{
            User user = userService.findById(id);

            Post post = postRepository.findById(postId).get();
            post.increaseViewCount();

            LikedPost likedPost = isLike(user,post);

            return new PostInfoResponseDto(post, user, likedPost);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    @Transactional
    public PostInfoResponseDto updatePost(Long postId, CreatePostRequestDto createPostRequestDto, Long id){
        try{
            User user = userService.findById(id);
            Post post = postRepository.findByIdAndUserId(postId, user.getId());

            post.update(createPostRequestDto.getArea(), createPostRequestDto.getBody(), createPostRequestDto.getTitle(),
                     createPostRequestDto.getFee(), createPostRequestDto.getCategory());

            return new PostInfoResponseDto(post, user);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    @Transactional
    public PostInfoResponseDto deletePost(Long postId, Long id){
        try{
            User user = userService.findById(id);
            Post post = postRepository.findByIdAndUserId(postId, user.getId());
            post.delete();

            return new PostInfoResponseDto(post, user);
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    public List<PostInfoResponseDto> findAllLikedPostsByUser(Long id){
        try {
            User user = userService.findById(id);

            List<Post> posts = new ArrayList<>();
            List<LikedPost> postLikes = postLikedRepository.findByUserIdAndIsDeletedIsFalse(user.getId());

            for(LikedPost likedPost : postLikes){
                posts.add(likedPost.getPost());
            }

            return posts.stream().map(post
                    -> new PostInfoResponseDto(post,user)).collect(Collectors.toList());

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    public LikedPost isLike(User user, Post post){
        try{
            return postLikedRepository.findByUserIdAndPostId(user.getId(), post.getId());
        }catch(RuntimeException e){
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    @Transactional
    public LikedInfoResponseDto saveLike(Long postId, Long id) {
        try {
            User user = userService.findById(id);

            Post post = postRepository.findById(postId).get();

            if(postLikedRepository.findByUserIdAndPostId(user.getId(),postId)==null) {
                LikedPost likedPost = LikedPost.builder()
                        .post(post)
                        .user(user)
                        .build();

                postLikedRepository.save(likedPost);

                return new LikedInfoResponseDto(likedPost);

            }else{

                LikedPost likedPost = postLikedRepository.findByUserIdAndPostId(user.getId(), post.getId());

                likedPost.save();

                return new LikedInfoResponseDto(likedPost);
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }

    }

    @Transactional
    public LikedInfoResponseDto deletedLike(Long likeId, Long id) {
        try {
            User user = userService.findById(id);

            LikedPost likedPost = postLikedRepository.findByIdAndUserId(likeId,user.getId());

            likedPost.delete();

            return new LikedInfoResponseDto(likedPost);

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }

    }

    @Transactional
    public void savePhoto(Post post, List<MultipartFile> files) throws Exception {
        if (files.size() != 0) {
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/photos/postPhoto";
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
    public Page<Post> searchPost(String keyword, Pageable pageable)
    {
        try {
            int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
            return postRepository.findByTitleContainingOrAreaContaining(keyword, pageable);
        }catch(RuntimeException e){
            e.getStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }

    }

    public SliceResponse<PostInfoResponseDto> findPostsByMbti(Long id, Pageable pageable){
        try {
            User user = userService.findById(id);
            List<User> sameGenderUsers = userRepository.findByGender(user.getGender());
            Mbti mbti = mbtiRepository.findByMbti(user.getMbti());
            List<Post> posts = new ArrayList<>();{
                for (User sameGenderUser : sameGenderUsers) {
                    if (sameGenderUser.getMbti().equals(mbti.getFirstMbti()) ||
                            sameGenderUser.getMbti().equals(mbti.getSecondMbti()) || sameGenderUser.getMbti()
                            .equals(mbti.getThirdMbti()) || sameGenderUser.getMbti().equals(mbti.getFourthMbti())) {
                        for (int i = 0; i < sameGenderUser.getPosts().size(); i++) {
                            if (sameGenderUser.getPosts().get(i).getIsDeleted() == false && posts.size()!=5) {
                                posts.add(sameGenderUser.getPosts().get(i));
                            }
                        }
                    }
                }
            }
            List<PostInfoResponseDto> postDtos = posts.stream()
                    .map(post -> new PostInfoResponseDto(post))
                    .collect(Collectors.toList());

            int page = pageable.getPageNumber();
            int size = pageable.getPageSize();

            return convertToSliceResponse(postDtos, page, size);

        }catch(RuntimeException e){
            e.getStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    public SliceResponse<PostInfoResponseDto> convertToSliceResponse(List<PostInfoResponseDto> postDtos, int page, int size) {
        int totalElements = postDtos.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        List<PostInfoResponseDto> pageContent = postDtos.stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());

        return new SliceResponse<>(page, size, page < totalPages - 1, pageContent);
    }

    public SliceResponse<PostInfoResponseDto> findPopularPosts(Pageable pageable){
        try {
            Slice<Post> posts = postRepository.findTop5ByOrderByViewCountPlusLikeCountDesc(pageable);

           return new SliceResponse(posts.map(post -> new PostInfoResponseDto(post)));

        }catch (RuntimeException e){
            e.getStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }
}