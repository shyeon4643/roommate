import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Comment from "./comment";

function DetailPost(){
    const { category, postId } = useParams();
    const [Post, setPost] = useState(null);


    useEffect(()=>{
        try{
            axios({
                method : "GET",
                url : `/post/${category}/${postId}`,
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: localStorage.getItem('JWT'),
                  },
            }).then((response) => {
                setPost(response.data.data);
            });
        }catch(error){
            console.log("게시글 불러오는 중에 오류 : ", error);
        }
        });

        const {
            title,
            imageUrl,
            body,
            likeCount,
            viewCount,
            comments,
          } = setPost;

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
            <Comment comments={comments}/>
        </div>
    )
}

export default DetailPost;