import axios from "axios";
import React, { useState } from "react";
import { useParams } from "react-router-dom";
import "../../css/comment.css";


function Comment({comments,user}){

const [newCommentText, setNewCommentText] = useState("");
const [editCommentText, setEditCommentText] = useState("");
const [editCommentId, setEditCommentId] = useState(null);
const [showCommentButton, setShowCommentButton] = useState(true);
const {category, postId} = useParams();

const handleEdit = (response) => {
        setEditCommentId(response.commentId);
        setEditCommentText(response.body);
    };

const handleNewComment = async () =>{
    try{
        const data ={
            body : newCommentText,
        }
     await axios({
         headers: {
             'Content-Type': 'application/json',
             'JWT': localStorage.getItem('JWT'),
         },
         method: "POST",
         url : `/post/${postId}/comment`,
         data : data,
     }).then((response) => {
         console.log("댓글 등록 완료:", response);
        setNewCommentText("");
        setShowCommentButton(false);
        window.location.reload();
     })
    }catch(error){
        console.log("댓글 등록 오류 : ", error);
    }

 };

    const handleCancelEdit = () => {
        setEditCommentId(null);
        setEditCommentText("");
    };
    const canEditOrDelete = (comment) => {
        if (user === comment.writerUser) {
            return true;
        }
        return false;
    };
 const handleEditComment = async(commentId)=>{
    try{
        await axios({
            headers: {
                'Content-Type': 'application/json',
                'JWT': localStorage.getItem('JWT'),
            },
            method : "PATCH",
            url : `/post/${postId}/comment/${commentId}`,
            data : { body : editCommentText}
        }).then((response) => {
            setEditCommentText("");
            window.location.reload();
        });
    }catch(error){
        console.log("댓글 수정 오류 : ", error);
    }
 }

 const handleDeleteComment = async(response)=>{
    try{
        const data = editCommentText;
        await axios({
            headers: {
                'Content-Type': 'application/json',
                'JWT': localStorage.getItem('JWT'),
            },
            method : "DELETE",
            url : `/post/${postId}/comment/${response.commentId}`,
            data : { body : data}
        }).then((response) => {
            window.location.reload();
        });
    }catch(error){
        console.log("댓글 삭제 오류 : ", error);
    }
 }
    console.log("댓글 데이터:", comments);
    return (
        <div className="comment_wrapper">
            <div className="comment_input_container">
                <input className="comment_input"
                type="text"
                placeholder="댓글을 입력하세요..."
                value={newCommentText}
                onChange={(e) => setNewCommentText(e.target.value)}
                onClick={()=>setShowCommentButton(true)}
                />
                {showCommentButton && (<button
                    className="comment-submit-button"
                    onClick={handleNewComment}
                >
                  댓글 등록
                </button>
                )}
            </div>
            <div className="comment_list_container">
                <ul className="comment_list">
                    {comments && comments.map((response, index) => (
                        <li key={response.commentId}>
                            <div className="comment_data">
                                <div className="comment_writer_date">
                                <p className="comment_writer">{response.writer}</p>
                                <p >{response.updateAt}</p>
                                </div>
                                <div className="comment_text_button">
                                {editCommentId === response.commentId ? (
                                    <textarea
                                        className="editComment_textarea"
                                        value={editCommentText}
                                        onChange={(e) => setEditCommentText(e.target.value)}
                                    />
                                ) : (
                                    <p className="comment_body">{response.body}</p>
                                )}

                            {editCommentId === response.commentId ? (
                                <div className="comment_actions">
                                    <button onClick={() => handleEditComment(response.commentId)}>저장</button>
                                    <button onClick={handleCancelEdit}>취소</button>
                                </div>
                            ) : (
                                <div className="comment_actions">
                                    {canEditOrDelete(response) && (
                                        <div>
                                            <button type="button" onClick={() => handleEdit(response)}>수정</button>
                                            <button type="button" onClick={() => handleDeleteComment(response)}>삭제</button>
                                        </div>
                                    )}
                                </div>
                            )}
                            </div>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>

    );
}

export default Comment;