package com.roommate.roommate.post.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.common.PageResponse;
import com.roommate.roommate.common.SliceResponse;
import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.dto.request.CreatePostRequestDto;
import com.roommate.roommate.post.dto.response.LikedInfoResponseDto;
import com.roommate.roommate.post.dto.response.PostInfoResponseDto;
import com.roommate.roommate.post.service.PostService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @ApiOperation(value = "게시글 등록")
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


    @ApiOperation(value = "내가 쓴 게시글 단건 조회")
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


    @ApiOperation(value = "카테고리별 게시글 조회")
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

    @ApiOperation(value = "게시글 검색")
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


    @ApiOperation(value = "게시물 단건 조회")
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

    @ApiOperation(value = "게시글 수정")
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


    @ApiOperation(value = "게시글 삭제")
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


    @ApiOperation(value = "좋아요한 게시글 다건 조회")
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

    @ApiOperation(value = "좋아요 등록")
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

    @ApiOperation(value = "좋아요 취소")
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


    @ApiOperation(value = "mbti 게시물")
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

    @ApiOperation(value = "인기 게시물")
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