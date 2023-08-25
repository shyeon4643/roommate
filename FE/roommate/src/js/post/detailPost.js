import React from "react";
import Comment from "./comment";

function DetailPost({postId, title, body, category, likeCount, viewCount, imageUrl, comment}){
    return(
        <div className="detailPost_container">
            <div className="detailPost_header">
                <h1>{category}</h1>
            </div>
            <div className="detailPost_body_header">
                <img src={imageUrl} alt="게시물 이미지" className="post-image" />
                <h2>{title}</h2>
                <p>{body}</p>
            </div>
            <div className="detailPost_body_footer">
                <p>추천수: {likeCount}</p>
                <p>조회수: {viewCount}</p>
            </div>
            <Comment comment={comment}/>
        </div>
    )
}

export default DetailPost;