package com.roommate.roommate.post.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.common.PageResponse;
import com.roommate.roommate.common.SliceResponse;
import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.domain.LikedPost;
import com.roommate.roommate.post.dto.request.CreatePostRequestDto;
import com.roommate.roommate.post.dto.response.LikedInfoResponseDto;
import com.roommate.roommate.post.dto.response.PostInfoResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
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
    @PostMapping(value="/post",consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponseDto> savePost(HttpServletRequest servletRequest,
                                                       CreatePostRequestDto createPostRequestDto)
            throws Exception{
            Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
            User user = userService.findById(id);
            Post post = postService.savePost(createPostRequestDto, user);
            PostInfoResponseDto response = new PostInfoResponseDto(post,user);
            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("POST_REGISTERED")
                            .responseMessage("게시글 등록 완료")
                            .data(response)
                            .build());

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
    @GetMapping("/user/post")
    public ResponseEntity<DefaultResponseDto> findAllPostsByUser(HttpServletRequest servletRequest){
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);
        Post post = postService.findUserPost(user);

        PostInfoResponseDto response = new PostInfoResponseDto(post, user);

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
    @GetMapping("/posts/{category}")
    public ResponseEntity<DefaultResponseDto> findAllPostsByCategory(@PathVariable("category")String category,
                                                                     HttpServletRequest servletRequest,
                                                                     Pageable pageable){
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);
        Page<Post> posts = postService.findAllPostsByCategory(category, pageable);

        PageResponse<PostInfoResponseDto> response = new PageResponse<>(new PageImpl<>(posts.getContent().stream().map(post
                -> new PostInfoResponseDto(post,user)).collect(Collectors.toList()),
                posts.getPageable(),
                posts.getTotalElements()));

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POSTS_FOUND")
                        .responseMessage("카테고리별 게시글 조회")
                        .data(response)
                        .build());
    }

    @ApiOperation(value="게시글 검색")
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
    @GetMapping("/search")
    public ResponseEntity<DefaultResponseDto> searchPost(@PageableDefault Pageable pageable,
                                                         @RequestParam ("keyword")String keyword){

        Page<Post> posts = postService.searchPost(keyword,pageable);

        List<PostInfoResponseDto> response = posts.getContent().stream()
                .map(post -> new PostInfoResponseDto(post))
                .collect(Collectors.toList());

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
    @GetMapping("/posts/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> findOnePost(@PathVariable("postId") Long postId,
                                                          HttpServletRequest servletRequest){
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);

        Post post = postService.findOnePost(postId);

        LikedPost likedPost = postService.isLike(user,post);
        PostInfoResponseDto response = new PostInfoResponseDto(post,user,likedPost);
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
    @PatchMapping("/posts/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> updatePost(@PathVariable("postId") Long postId,
                                                         @RequestBody CreatePostRequestDto createPostRequestDto,
                                                         HttpServletRequest servletRequest){
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);
        Post post = postService.updatePost(postId, createPostRequestDto, user);

        PostInfoResponseDto response = new PostInfoResponseDto(post,user);
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
    @DeleteMapping("/posts/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> deletePost(@PathVariable("postId") Long postId,
                                                         HttpServletRequest servletRequest){
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);
        Post post = postService.deletePost(postId,user);

        PostInfoResponseDto response = new PostInfoResponseDto(post,user);
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
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        User user = userService.findById(id);
        List<Post> posts = postService.findAllLikedPostsByUser(user);

        List<PostInfoResponseDto> response = posts.stream().map(post
                -> new PostInfoResponseDto(post,user)).collect(Collectors.toList());
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("LIKED_POST_FOUND")
                        .responseMessage("좋아요한 게시글 다건 조회 완료")
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
    @PatchMapping("/{category}/posts/{postId}/like")
    public ResponseEntity<DefaultResponseDto> saveLike(@PathVariable("postId") Long postId,
                                                       HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        LikedPost likedPost = postService.saveLike(postId,id);

        LikedInfoResponseDto response = new LikedInfoResponseDto(likedPost);
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
    @PatchMapping("/{category}/posts/{postId}/like/{likeId}")
    public ResponseEntity<DefaultResponseDto> deletedLike(@PathVariable("postId") Long postId,
                                                          @PathVariable("likeId") Long likeId,
                                                          HttpServletRequest servletRequest) {
            Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
            LikedPost likedPost = postService.deletedLike(likeId, id);

            LikedInfoResponseDto response = new LikedInfoResponseDto(likedPost);
            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("LIKE_WORK")
                            .responseMessage("좋아요 등록/취소")
                            .data(response)
                            .build());

    }


    @ApiOperation(value = "홈 게시물")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "FIND_POSTS",
                    content = @Content(schema = @Schema(implementation = LikedInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "MBTI_POSTS_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/home/posts")
    public ResponseEntity<DefaultResponseDto> homePosts(HttpServletRequest servletRequest, Pageable pageable) {
        String jwtHeader = servletRequest.getHeader("JWT");
        Slice<Post> posts;
        if(jwtHeader != null && !jwtHeader.isEmpty()){
            Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
            User user = userService.findById(id);
            posts = postService.homePosts(user, pageable);

            SliceResponse<PostInfoResponseDto> response = new SliceResponse(posts.map(post
                    -> new PostInfoResponseDto(post)));

            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("FIND_POSTS")
                            .responseMessage("게시물 조회 완료")
                            .data(response)
                            .build());
        }else {
            posts = postService.findPostsByRecent(pageable);

            SliceResponse<PostInfoResponseDto> response = new SliceResponse(posts.map(post
                    -> new PostInfoResponseDto(post)));
            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("FIND_POSTS")
                            .responseMessage("게시물 조회 완료")
                            .data(response)
                            .build());
        }
    }

}
