import axios from "axios";
import React, { useEffect, useState } from "react";
import { FaHeart, FaRegHeart } from 'react-icons/fa';
import { useNavigate, useParams } from "react-router-dom";
import "../../css/detailPost.css";
import Comment from "./comment";
import myImage from '../../photo.png';

function DetailPost(){
    const { category, postId } = useParams();
    const [selectedPost, setSelectedPost] = useState("");
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        try {
            axios({
                method: "GET",
                url: `/posts/${category}/${postId}`,
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
            method: "PATCH",
            url: `/posts/${category}/${postId}/like`,
        }).then((result) => {
            window.location.href = `/${category}/posts`;
        });
    };

    const isntLike = () => {
        axios({
            headers: {
                'JWT': localStorage.getItem('JWT'),
            },
            method: "PATCH",
            url: `/posts/${category}/${postId}/like/${selectedPost.likedId}`,
        }).then((result) => {
            window.location.href = `/${category}/posts`;
        });
    };

    const handleLikeClick = () => {
        if (selectedPost.likedId === null) {
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
            <div className="detailPost_body">
            <div className="detailPost_body_header">
                <h1 id="detailPost_title">{selectedPost.title}</h1>
                <div className="detailPost_edit_delete_button">
                    <div className="detailPost_edit_img_writer">
                    <img src={myImage} alt="My Image" className="detailPost_img"/>
                    {selectedPost.writer}
                    </div>
            {canEditOrDelete() && (
                    <div className="detailPost_post_button">
                        <button
                        typy="button"
                        onClick={postUpdate}>수정</button>
                        <button
                        type="button"
                        onClick={deletePost}>삭제</button>
                    </div>
                )}
                </div>
                <div className="detailPost_count_heart">
                <div className="detailPost_count">
                <p>좋아요 : {selectedPost.likeCount}</p>
                </div>
                <div className="detailPost_heart">
                {selectedPost.like == false? (
                    <FaHeart className="heart-icon" onClick={handleLikeClick} />
                ) : (
                    <FaRegHeart className="heart-icon" onClick={handleLikeClick} />
                )}
                </div>
                </div>
            </div>
            <div className="detailPost_body_body">
                {selectedPost.path && selectedPost.path.map((path, index) => (
                <img key={index} src={`/static/photos/postPhotos/${path}`} alt={`Post Photo ${index}`} />

  ))}
  <p>{selectedPost.body}</p>
        </div>
    
        </div>
            <div className="detailPost_body_footer">
                <Comment comments={selectedPost.comments} user={user}/>
            </div>
            </div>
    )
}

export default DetailPost;