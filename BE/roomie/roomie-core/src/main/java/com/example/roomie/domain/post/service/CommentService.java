package com.example.roomie.domain.post.service;

import com.example.roomie.domain.post.Comment;
import com.example.roomie.domain.post.Post;
import com.example.roomie.domain.post.repository.CommentRepository;
import com.example.roomie.domain.post.repository.PostRepository;
import com.example.roomie.domain.user.User;
import com.example.roomie.domain.user.service.UserService;
import com.roomie.roomie.exception.CustomException;
import com.roomie.roomie.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    /*
     * 댓글 등록
     * 500(SERVER_ERROR)
     */
    @Transactional
    public CommentInfoResponseDto saveComment(Long postId, CreateCommentRequestDto createCommentRequestDto,
                                              Long id){
        try{
            User user = userService.findById(id);
            Post post = postRepository.findById(postId).get();
            Comment comment = Comment.builder()
                    .body(createCommentRequestDto.getBody())
                    .post(post)
                    .user(user)
                    .build();

            commentRepository.save(comment);

            return new CommentInfoResponseDto(comment);

        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    /*
     * 댓글 수정
     * 500(SERVER_ERROR)
     * */
    @Transactional
    public CommentInfoResponseDto updateComment(Long id, Long commentId, CreateCommentRequestDto createCommentRequestDto){
        try{
            User user = userService.findById(id);
            Comment comment = commentRepository.findByIdAndUserId(commentId,user.getId());
            comment.update(createCommentRequestDto.getBody());

            return new CommentInfoResponseDto(comment);
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    /*
     * 내가 쓴 댓글 다건 조회
     * 500(SERVER_ERROR)
     * */
    @Transactional(readOnly = true)
    public List<CommentInfoResponseDto> findAllCommentByUser(Long id){
        try{
            User user = userService.findById(id);
            List<Comment> comments = commentRepository.findByUserIdAndIsDeletedIsFalse(user.getId());
            return comments.stream().map(comment
                    -> new CommentInfoResponseDto(comment)).collect(Collectors.toList());
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    /*
     * 내가 쓴 댓글 삭제
     * 500(SERVER_ERROR)
     * */
    @Transactional
    public CommentInfoResponseDto deleteComment(Long commentId, Long id){
        try{
            User user = userService.findById(id);
            Comment comment = commentRepository.findByIdAndUserIdAndIsDeletedIsFalse(commentId,user.getId());
            comment.delete();
            return new CommentInfoResponseDto(comment);
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(e, ExceptionCode.SERVER_ERROR);
        }
    }

    /*@Transactional
    public void savePhoto(Comment comment, List<MultipartFile> files) throws Exception {
        if (files.size() != 0) {
            String projectPath = System.getProperty("user.dir") + "\\\\BE\\\\school\\\\src\\\\main\\\\resources\\\\static\\\\comments";
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String extension = filename.substring(filename.lastIndexOf("."));
                String saveFilename = UUID.randomUUID()+extension;

                File saveFile = new File(projectPath, saveFilename);
                file.transferTo(saveFile);

                CommentPhoto commentPhoto = CommentPhoto.builder()
                        .photoUrl(saveFilename)
                        .comment(comment)
                        .build();
                commentPhotoRepository.save(commentPhoto);
            }

        }
    }*/

}
