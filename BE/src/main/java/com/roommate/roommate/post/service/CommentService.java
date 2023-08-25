package com.roommate.roommate.post.service;

import com.roommate.roommate.post.domain.Comment;
import com.roommate.roommate.post.domain.Post;
import com.roommate.roommate.post.dto.request.CreateCommentRequestDto;
import com.roommate.roommate.post.repository.CommentRepository;
import com.roommate.roommate.post.repository.PostRepository;
import com.roommate.roommate.user.domain.User;
import com.roommate.roommate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import com.roommate.roommate.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.roommate.roommate.exception.ExceptionCode.SERVER_ERROR;

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
    public Comment saveComment(Long postId, CreateCommentRequestDto createCommentRequestDto,
                               String uid){
        try{
            User user = userService.findByUid(uid);
            Post post = postRepository.findById(postId).get();
            Comment comment = Comment.builder()
                    .body(createCommentRequestDto.getBody())
                    .post(post)
                    .user(user)
                    .build();

            commentRepository.save(comment);
            return comment;
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 댓글 수정
     * 500(SERVER_ERROR)
     * */
    @Transactional
    public Comment updateComment(String uid, Long commentId, CreateCommentRequestDto createCommentRequestDto){
        try{
            User user = userService.findByUid(uid);
            Comment comment = commentRepository.findByIdAndUserId(commentId,user.getId());
            comment.update(createCommentRequestDto.getBody());

            return comment;
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 내가 쓴 댓글 다건 조회
     * 500(SERVER_ERROR)
     * */
    @Transactional(readOnly = true)
    public List<Comment> findAllCommentByUser(String uid){
        try{
            User user = userService.findByUid(uid);
            List<Comment> comments = commentRepository.findByUserIdAndIsDeletedIsFalse(user.getId());
            return comments;
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

    /*
     * 내가 쓴 댓글 삭제
     * 500(SERVER_ERROR)
     * */
    @Transactional
    public Comment deleteComment(Long commentId, String uid){
        try{
            User user = userService.findByUid(uid);
            Comment comment = commentRepository.findByIdAndUserIdAndIsDeletedIsFalse(commentId,user.getId());
            comment.delete();
            return comment;
        }catch(RuntimeException e){
            e.printStackTrace();
            throw new CustomException(SERVER_ERROR);
        }
    }

   /* @Transactional
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
