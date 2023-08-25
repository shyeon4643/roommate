import React from "react";


const handleComment = async () =>{

}


function Comment({comment}){
    return (
        <div className="comment">
            <div className="comment_input_container">
                <input className="comment_input" 
                type="text"
                placeholder="댓글을 입력하세요..."/>
                <button
                    className="comment-submit-button"
                    onClick={handleComment}
                >
                  댓글 등록
                </button>
            </div>
        </div>
    )
}

export default Comment;