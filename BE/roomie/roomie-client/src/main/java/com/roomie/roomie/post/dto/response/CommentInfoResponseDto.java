package com.roomie.roomie.post.dto.response;

import com.roommate.roommate.post.domain.Comment;
import com.roommate.roommate.post.domain.PostCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(value = "Comment 기본 응답")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentInfoResponseDto {

    @ApiModelProperty(position = 1, required = true, value = "Post 식별자", example = "1")
    private Long commentId;
    private Long postId;
    private String writer;
    private String body;
    private Long writerUser;
    private PostCategory category;
    private boolean commentDeleted;
    private LocalDateTime updateAt;

    public CommentInfoResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.commentDeleted = comment.getIsDeleted();
        this.category = comment.getPost().getCategory();
        this.writer = comment.getUser().getNickname();
        this.writerUser = comment.getUser().getId();
        this.updateAt = comment.getUpdatedAt();
        this.body = comment.getBody();
    }
}
