import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Comment from "./comment";

function DetailPost(){
    const { category, postId } = useParams();
    const [selectedPost, setSelectedPost] = useState(""); // 변경: setSelectedPost를 함수로 설정

    useEffect(() => {
        try {
            axios({
                method: "GET",
                url: `/post/${category}/${postId}`,
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
            }).then((response) => {
                console.log(response.data.data);
                setSelectedPost(response.data.data);
            });
        } catch (error) {
            console.log("게시글 불러오는 중에 오류 : ", error);
        }
    }, [category, postId]);

    return(
        <div className="detailPost_container">
            <div className="detailPost_header">
                <h1>{category === 'charter' ? '전세' : '월세'}</h1>
            </div>
            <div className="detailPost_body_header">
                <h2>{selectedPost.title}</h2>
                <p>추천수: {selectedPost.likeCount}</p>
                <p>조회수: {selectedPost.viewCount}</p>
            </div>
            <div className="detailPost_body_body">
                <p>{selectedPost.body}</p>
            </div>
            <div className="detailPost_body_footer">
                <Comment comments={selectedPost.comments}/>
            </div>
        </div>
    )
}

export default DetailPost;