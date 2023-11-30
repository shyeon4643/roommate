package com.roommate.roommate.post.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.post.domain.Comment;
import com.roommate.roommate.post.dto.request.CreateCommentRequestDto;
import com.roommate.roommate.post.dto.response.CommentInfoResponseDto;
import com.roommate.roommate.post.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "댓글")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "댓글 등록")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "COMMENT_SAVE",
                    content = @Content(schema = @Schema(implementation = CommentInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "COMMENT_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PostMapping(value="/post/{postId}/comment")
    public ResponseEntity<DefaultResponseDto> saveComment(@PathVariable("postId") Long postId,
                                                          @RequestBody CreateCommentRequestDto createCommentRequestDto,
                                                          HttpServletRequest servletRequest) throws Exception{
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        Comment comment = commentService.saveComment(postId, createCommentRequestDto,id);

        CommentInfoResponseDto response = new CommentInfoResponseDto(comment);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_REGISTERED")
                        .responseMessage("댓글 등록 완료")
                        .data(response)
                        .build());
    }

    @ApiOperation(value = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "COMMENT_UPDATE",
                    content = @Content(schema = @Schema(implementation = CommentInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "COMMENT_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @PatchMapping(value="/post/{postId}/comment/{commentId}")
    public ResponseEntity<DefaultResponseDto> updateComment(@RequestBody CreateCommentRequestDto createCommentRequestDto,
                                                            @PathVariable("commentId") Long commentId,
                                                            HttpServletRequest servletRequest){
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
            Comment comment = commentService.updateComment(id,commentId, createCommentRequestDto);

            CommentInfoResponseDto response = new CommentInfoResponseDto(comment);
            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("COMMENT_UPDATE")
                            .responseMessage("댓글 수정")
                            .data(response)
                            .build());


    }

    @ApiOperation(value = "내가 쓴 댓글 다건 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "COMMENTS_FOUND",
                    content = @Content(schema = @Schema(implementation = CommentInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "COMMENT_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @GetMapping("/user/comments")
    public ResponseEntity<DefaultResponseDto> findAllCommentsByUser(HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));
        List<Comment> comments = commentService.findAllCommentByUser(id);

        List<CommentInfoResponseDto> response = comments.stream().map(comment
                -> new CommentInfoResponseDto(comment)).collect(Collectors.toList());
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("COMMENTS_FOUND")
                        .responseMessage("내가 쓴 댓글 다건 조회 완료")
                        .data(response)
                        .build());
    }


    @ApiOperation(value = "내가 쓴 댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "COMMENT_DELETED",
                    content = @Content(schema = @Schema(implementation = CommentInfoResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "UNAUTHORIZED_MEMBER"),
            @ApiResponse(responseCode = "404",
                    description = "COMMENT_NOT_FOUND"),
            @ApiResponse(responseCode = "500",
                    description = "SERVER_ERROR"),
    })
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<DefaultResponseDto> deleteComment(@PathVariable("commentId") Long commentId,
                                                            HttpServletRequest servletRequest) {
            Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

            Comment comment = commentService.deleteComment(commentId,id);


            CommentInfoResponseDto response = new CommentInfoResponseDto(comment);
            return ResponseEntity.status(200)
                    .body(DefaultResponseDto.builder()
                            .responseCode("COMMENT_DELETED")
                            .responseMessage("내가 쓴 댓글 삭제")
                            .data(response)
                            .build());

    }


}
