import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../../css/detailPost.css";
import Comment from "./comment";
import { FaHeart, FaRegHeart } from 'react-icons/fa';

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

    const isLike = () => {
        axios({
            headers: {
                'JWT': localStorage.getItem('JWT'),
            },
            method: "PUT",
            url: `/post/${category}/${postId}/like`,
        }).then((result) => {
            window.location.href = `/${category}/posts`;
        });
    };

    const isntLike = () => {
        axios({
            headers: {
                'JWT': localStorage.getItem('JWT'),
            },
            method: "Put",
            url: `/post/${category}/${postId}/like/${selectedPost.likedId}`,
        }).then((result) => {
            window.location.href = `/${category}/posts`;
        });
    };

    const handleLikeClick = () => {
        if (selectedPost.likeCount === 0) {
            isLike();
        } else {
            isntLike();
        }
    };

    return(
        <div className="detailPost_container">
            <div className="detailPost_header">
                <h1>{category === 'charter' ? '전세' : '월세'}</h1>
            </div>
            <div className="detailPost_body_header">
                <h2 id="detailPost_title">{selectedPost.title}</h2>
                <p>추천수: {selectedPost.likeCount}</p>
                <p>조회수: {selectedPost.viewCount}</p>
                {selectedPost.like == false? (
                    <FaHeart className="heart-icon-filled" onClick={handleLikeClick} />
                ) : (
                    <FaRegHeart className="heart-icon-empty" onClick={handleLikeClick} />
                )}
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