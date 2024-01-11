package com.roomie.roomie.post.controller;

import com.roommate.roommate.common.DefaultResponseDto;
import com.roommate.roommate.config.security.JwtTokenProvider;
import com.roommate.roommate.post.domain.Comment;
import com.roommate.roommate.post.dto.request.CreateCommentRequestDto;
import com.roommate.roommate.post.dto.response.CommentInfoResponseDto;
import com.roommate.roommate.post.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "댓글")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "댓글 등록")
    @PostMapping(value = "/post/{postId}/comment")
    public ResponseEntity<DefaultResponseDto> saveComment(@PathVariable("postId") Long postId,
                                                          @RequestBody CreateCommentRequestDto createCommentRequestDto,
                                                          HttpServletRequest servletRequest) throws Exception {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));


        CommentInfoResponseDto response = commentService.saveComment(postId, createCommentRequestDto, id);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("POST_REGISTERED")
                        .responseMessage("댓글 등록 완료")
                        .data(response)
                        .build());
    }

    @ApiOperation(value = "댓글 수정")
    @PatchMapping(value = "/post/{postId}/comment/{commentId}")
    public ResponseEntity<DefaultResponseDto> updateComment(@RequestBody CreateCommentRequestDto createCommentRequestDto,
                                                            @PathVariable("commentId") Long commentId,
                                                            HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        CommentInfoResponseDto response = commentService.updateComment(id, commentId, createCommentRequestDto);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("COMMENT_UPDATE")
                        .responseMessage("댓글 수정")
                        .data(response)
                        .build());


    }

    @ApiOperation(value = "내가 쓴 댓글 다건 조회")
    @GetMapping("/user/comments")
    public ResponseEntity<DefaultResponseDto> findAllCommentsByUser(HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        List<CommentInfoResponseDto> response = commentService.findAllCommentByUser(id);
        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("COMMENTS_FOUND")
                        .responseMessage("내가 쓴 댓글 다건 조회 완료")
                        .data(response)
                        .build());
    }


    @ApiOperation(value = "내가 쓴 댓글 삭제")
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<DefaultResponseDto> deleteComment(@PathVariable("commentId") Long commentId,
                                                            HttpServletRequest servletRequest) {
        Long id = Long.parseLong(jwtTokenProvider.getUsername(servletRequest.getHeader("JWT")));

        CommentInfoResponseDto response = commentService.deleteComment(commentId, id);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("COMMENT_DELETED")
                        .responseMessage("내가 쓴 댓글 삭제")
                        .data(response)
                        .build());

    }


}
