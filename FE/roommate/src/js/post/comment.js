import axios from "axios";
import React, { useState } from "react";
import { useParams } from "react-router-dom";


function Comment({comments,user}){

const [newCommentText, setNewCommentText] = useState("");
const [editCommentText, setEditCommentText] = useState("");
const [showCommentButton, setShowCommentButton] = useState(true);
const {category, postId} = useParams();

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
         url : `/post/${category}/${postId}/comment`,
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
    const canEditOrDelete = (comment) => {
        if (user === comment.writerUser) {
            return true;
        }
        return false;
    };
 const handleEditComment = async(response)=>{
    try{
        await axios({
            method : "PUT",
            url : `/post/${category}/${postId}/comment/${response.commentId}`,
            data : { comment : data}
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
            method : "DELETE",
            url : `/post/${category}/${postId}/comment/${response.commentId}`,
            data : { comment : data}
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
                    {comments&&comments.map((response, index) => (
                        <li key={response.commentId}>
                        <div className="comment_data">
                            <p>{index+1}</p>
                            <p>{response.writer}</p>
                            <p>{response.body}</p>
                            <p>{response.updateAt}</p>
                        </div>
                            {canEditOrDelete(response) && (
                                <div>
                                    <button
                                        typy="button"
                                        onClick={()=>handleEditComment(response)}>수정</button>
                                    <button
                                        type="button"
                                        onClick={()=>handleDeleteComment(response)}>삭제</button>
                                </div>
                            )}
                    </li>
                    ))}
                </ul>
            </div>
        </div>

    );
}

export default Comment;