package com.roommate.roommate.post.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.domain.LikedPhoto;
import com.roommate.roommate.post.dto.request.CreatePostRequestDto;
import com.roommate.roommate.post.dto.response.LikedInfoResponseDto;
import com.roommate.roommate.post.dto.response.PostInfoResponseDto;
import com.roommate.roommate.post.repository.PostRepository;
import com.roommate.roommate.post.service.PostService;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "게시글")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value="게시글 등록")
    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "200",
                    description = "POST_SAVE",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "ALREADY_HAVE_POST"),
            @ApiResponse(responseCode = "404",
                    description = "POST_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PostMapping(value="/writePost",consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponseDto> savePost(HttpServletRequest servletRequest,
                                                       CreatePostRequestDto createPostRequestDto)
            throws Exception{
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        if(postService.findPostByUid(uid)!=null) {
            return ResponseEntity.status(401)
                    .body(DefaultResponseDto.builder()
                            .responseCode("ALREADY_HAVE_POST")
                            .responseMessage("게시글이 이미 있습니다.")
                            .data(null)
                            .build());
        }else{
            Post post = postService.savePost(createPostRequestDto, uid);
            PostInfoResponseDto response = new PostInfoResponseDto(post);
            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("POST_REGISTERED")
                            .responseMessage("게시글 등록 완료")
                            .data(response)
                            .build());
        }

    }



    @ApiOperation(value="내가 쓴 게시글 단건 조회")
    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "200",
                    description = "POSTS_FOUND",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "POSTS_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/user/posts")
    public ResponseEntity<DefaultResponseDto> findAllPostsByUser(HttpServletRequest servletRequest){
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        Post post = postService.findPostByUid(uid);

        PostInfoResponseDto response = new PostInfoResponseDto(post);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POSTS_FOUND")
                        .responseMessage("내가 쓴 게시글 다건 조회 완료")
                        .data(response)
                        .build());
    }


    @ApiOperation(value="카테고리별 게시글 조회")
    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "200",
                    description = "POSTS_FOUND",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "POSTS_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/{category}/posts")
    public ResponseEntity<DefaultResponseDto> findAllPostsByCategory(@PathVariable("category")String category){
        List<Post> posts = postService.findAllPostsByCategory(category);

        List<PostInfoResponseDto> response = posts.stream().map(post
                -> new PostInfoResponseDto(post)).collect(Collectors.toList());

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POSTS_FOUND")
                        .responseMessage("카테고리별 게시글 조회")
                        .data(response)
                        .build());
    }

    @ApiOperation(value="지역별 게시글 조회")
    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "200",
                    description = "POSTS_FOUND",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "POSTS_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/posts/{area}")
    public ResponseEntity<DefaultResponseDto> findAllPostsByArea(@PathVariable("area")String area){
        List<Post> posts = postService.findAllPostsByArea(area);

        List<PostInfoResponseDto> response = posts.stream().map(post
                -> new PostInfoResponseDto(post)).collect(Collectors.toList());

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POSTS_FOUND")
                        .responseMessage("지역별 게시글 조회")
                        .data(response)
                        .build());
    }


    @ApiOperation(value="게시물 단건 조회")
    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "200",
                    description = "POST_FOUND",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "POST_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/post/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> findOnePost(@PathVariable("postId") Long postId){

        Post post = postService.findOnePost(postId);
        PostInfoResponseDto response = new PostInfoResponseDto(post);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_FOUND")
                        .responseMessage("게시물 단건 조회")
                        .data(response)
                        .build());
    }

    @ApiOperation(value="게시글 수정")
    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "200",
                    description = "POST_UPDATEED",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "POST_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PutMapping("/post/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> updatePost(@PathVariable("postId") Long postId,
                                                         @RequestBody CreatePostRequestDto createPostRequestDto,
                                                         HttpServletRequest servletRequest){
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        Post post = postService.updatePost(postId, createPostRequestDto, uid);

        PostInfoResponseDto response = new PostInfoResponseDto(post);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_UPDATEED")
                        .responseMessage("게시글 수정 완료")
                        .data(response)
                        .build());
    }




    @ApiOperation(value="게시글 삭제")
    @ApiResponses(value ={
            @ApiResponse(
                    responseCode = "200",
                    description = "POST_DELETED",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "POST_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @DeleteMapping("/post/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> deletePost(@PathVariable("postId") Long postId,
                                                         HttpServletRequest servletRequest){
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        Post post = postService.deletePost(postId,uid);

        PostInfoResponseDto response = new PostInfoResponseDto(post);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_DELETED")
                        .responseMessage("게시글 삭제 완료")
                        .data(response)
                        .build());
    }


    @ApiOperation(value = "좋아요한 게시글 다건 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "LIKED_POST_FOUND",
                    content = @Content(schema = @Schema(implementation = PostInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "COMMENT_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/user/posts/likes")
    public ResponseEntity<DefaultResponseDto> findAllLikedPostsByUser(HttpServletRequest servletRequest) {
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        User user = userService.findByUid(uid);
        List<LikedPhoto> likes = postService.findAllLikedPostsByUser(uid);

        List<LikedInfoResponseDto> response = likes.stream().map(like
                -> new LikedInfoResponseDto(like,user)).collect(Collectors.toList());
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("LIKED_POST_FOUND")
                        .responseMessage("내가 쓴 게시글 조회 완료")
                        .data(response)
                        .build());
    }

    @ApiOperation(value = "좋아요 등록")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "LIKE_WORK",
                    content = @Content(schema = @Schema(implementation = LikedInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "COMMENT_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PutMapping("/post/{category}/{postId}/like")
    public ResponseEntity<DefaultResponseDto> saveLike(@PathVariable("postId") Long postId,
                                                       HttpServletRequest servletRequest) {
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
        LikedPhoto likedPhoto = postService.saveLike(postId,uid);

        LikedInfoResponseDto response = new LikedInfoResponseDto(likedPhoto);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("LIKE_WORK")
                        .responseMessage("좋아요 등록")
                        .data(response)
                        .build());
    }

    @ApiOperation(value = "좋아요 취소")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "LIKE_WORK",
                    content = @Content(schema = @Schema(implementation = LikedInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "COMMENT_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @DeleteMapping("/post/{category}/{postId}/like/{likeId}")
    public ResponseEntity<DefaultResponseDto> deletedLike(@PathVariable("postId") Long postId,
                                                          @PathVariable("likeId") Long likeId,
                                                          HttpServletRequest servletRequest) {
        String uid = jwtTokenProvider.getUsername(servletRequest.getHeader("JWT"));
            LikedPhoto likedPhoto = postService.deletedLike(likeId, uid);

            LikedInfoResponseDto response = new LikedInfoResponseDto(likedPhoto);
            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("LIKE_WORK")
                            .responseMessage("좋아요 등록/취소")
                            .data(response)
                            .build());

    }



}