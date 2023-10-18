import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Comment from "./comment";

function DetailPost(){
    const { category, postId } = useParams();
    const [selectedPost, setSelectedPost] = useState("");
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

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
                setUser(response.data.data.currentUser);
            });
        } catch (error) {
            console.log("게시글 불러오는 중에 오류 : ", error);
        }
    }, [category, postId]);

    const canEditOrDelete = () => {
        if (user === selectedPost.writerUser) {
            return true;
        }
        return false;
    };

    const postUpdate = () => {
        navigate("/writePost", {
            state: {
                postId: selectedPost.postId,
                category: selectedPost.category,
                title: selectedPost.title,
                body: selectedPost.body,
                fee: selectedPost.fee,
                files : selectedPost.photos,
                area:selectedPost.area,
                message: "수정 완료",
            },
        });
    };
    const deletePost = () => {
        const accessToken = getCookieValue("accessToken");
        axios({
            headers: {
                'JWT': localStorage.getItem('JWT'),
            },
            method: "DELETE",
            url: `/post/${category}/${postId}`,
        }).then((result) => {
            window.location.href = `/${category}/posts`;
        });
    };

    return(
        <div className="detailPost_container">
            <div className="detailPost_header">
                <h1>{category === 'charter' ? '전세' : '월세'}</h1>
            </div>
            <div className="detailPost_body_header">
                <h2>{selectedPost.title}</h2>
                <p>추천수: {selectedPost.likeCount}</p>
                <p>조회수: {selectedPost.viewCount}</p>
                {canEditOrDelete() && (
                    <div>
                        <button
                        typy="button"
                        onClick={postUpdate}>수정</button>
                        <button
                        type="button"
                        onClick={deletePost}>삭제</button>
                    </div>
                )}
            </div>
            <div className="detailPost_body_body">
                <p>{selectedPost.body}</p>
            </div>
            <div className="detailPost_body_footer">
                <Comment comments={selectedPost.comments} user={user}/>
            </div>
        </div>
    )
}

export default DetailPost;