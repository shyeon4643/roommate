package com.roomie.roomie.post.controller;

import com.example.roomie.common.response.PageResponse;
import com.example.roomie.common.response.SliceResponse;
import com.example.roomie.domain.post.Post;
import com.example.roomie.domain.post.service.PostService;
import com.example.roomie.domain.user.service.UserService;
import com.roomie.roomie.common.DefaultResponseDto;
import com.roomie.roomie.post.dto.request.CreatePostRequestDto;
import com.roomie.roomie.post.dto.response.LikedInfoResponseDto;
import com.roomie.roomie.post.dto.response.PostInfoResponseDto;
import com.roomie.roomie.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "게시글", description = "게시글 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "게시글 등록")
    @PostMapping(value = "/post", consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponseDto> savePost(HttpServletRequest servletRequest,
                                                       CreatePostRequestDto createPostRequestDto)
            throws Exception {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        PostInfoResponseDto response = postService.savePost(createPostRequestDto, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_REGISTERED")
                        .responseMessage("게시글 등록 완료")
                        .data(response)
                        .build());

    }


    @Operation(summary = "내가 쓴 게시글 단건 조회")
    @GetMapping("/user/post")
    public ResponseEntity<DefaultResponseDto> findPostByUser(HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        PostInfoResponseDto response = postService.findPostByUser(id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POSTS_FOUND")
                        .responseMessage("내가 쓴 게시글 다건 조회 완료")
                        .data(response)
                        .build());
    }


    @Operation(summary = "카테고리별 게시글 조회")
    @GetMapping("/posts/{category}")
    public ResponseEntity<DefaultResponseDto> findAllPostsByCategory(@PathVariable("category") String category,
                                                                     HttpServletRequest servletRequest,
                                                                     Pageable pageable) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        PageResponse<PostInfoResponseDto> response = postService.findAllPostsByCategory(category, pageable, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POSTS_FOUND")
                        .responseMessage("카테고리별 게시글 조회")
                        .data(response)
                        .build());
    }

    @Operation(summary = "게시글 검색")
    @GetMapping("/search")
    public ResponseEntity<DefaultResponseDto> searchPost(@PageableDefault Pageable pageable,
                                                         @RequestParam("keyword") String keyword) {

        Page<Post> posts = postService.searchPost(keyword, pageable);

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


    @Operation(summary = "게시물 단건 조회")
    @GetMapping("/posts/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> DetailPost(@PathVariable("postId") Long postId,
                                                         HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        PostInfoResponseDto response = postService.detailPost(id, postId);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_FOUND")
                        .responseMessage("게시물 단건 조회")
                        .data(response)
                        .build());
    }

    @Operation(summary = "게시글 수정")
    @ApiResponses(value = {
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
                                                         HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        PostInfoResponseDto response = postService.updatePost(postId, createPostRequestDto, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_UPDATEED")
                        .responseMessage("게시글 수정 완료")
                        .data(response)
                        .build());
    }


    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/post/{category}/{postId}")
    public ResponseEntity<DefaultResponseDto> deletePost(@PathVariable("postId") Long postId,
                                                         HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        PostInfoResponseDto response = postService.deletePost(postId, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_DELETED")
                        .responseMessage("게시글 삭제 완료")
                        .data(response)
                        .build());
    }


    @Operation(summary = "좋아요한 게시글 다건 조회")
    @GetMapping("/user/posts/likes")
    public ResponseEntity<DefaultResponseDto> findAllLikedPostsByUser(HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        List<PostInfoResponseDto> response = postService.findAllLikedPostsByUser(id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("LIKED_POST_FOUND")
                        .responseMessage("좋아요한 게시글 다건 조회 완료")
                        .data(response)
                        .build());
    }

    @Operation(summary = "좋아요 등록")
    @PatchMapping("/posts/{category}/{postId}/like")
    public ResponseEntity<DefaultResponseDto> saveLike(@PathVariable("postId") Long postId,
                                                       HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        LikedInfoResponseDto response = postService.saveLike(postId, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("LIKE_WORK")
                        .responseMessage("좋아요 등록")
                        .data(response)
                        .build());
    }

    @Operation(summary = "좋아요 취소")
    @PatchMapping("/posts/{category}/{postId}/like/{likeId}")
    public ResponseEntity<DefaultResponseDto> deletedLike(@PathVariable("likeId") Long likeId,
                                                          HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        LikedInfoResponseDto response = postService.deletedLike(likeId, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("LIKE_WORK")
                        .responseMessage("좋아요 등록/취소")
                        .data(response)
                        .build());

    }


    @Operation(summary = "mbti 게시물")
    @GetMapping("/home/posts/mbti")
    public ResponseEntity<DefaultResponseDto> mbtiPosts(HttpServletRequest servletRequest, Pageable pageable) {

        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        SliceResponse<PostInfoResponseDto> response = postService.findPostsByMbti(id, pageable);


        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("FIND_POSTS")
                        .responseMessage("게시물 조회 완료")
                        .data(response)
                        .build());


    }

    @Operation(summary = "인기 게시물")
    @GetMapping("/home/posts/popular")
    public ResponseEntity<DefaultResponseDto> popularPosts(HttpServletRequest servletRequest, Pageable pageable) {

        SliceResponse<PostInfoResponseDto> response = postService.findPopularPosts(pageable);


        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("FIND_POSTS")
                        .responseMessage("게시물 조회 완료")
                        .data(response)
                        .build());


    }
}