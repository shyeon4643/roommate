import axios from "axios";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../../css/sidebar.css";

function Sidebar(){
    const [likedPostsData, setLikedPostsData] = useState([]);

    const myPost = async () =>{
        try{
            axios({
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
                method: "GET",
                url: `/user/post`,
            }).then((response) =>{
                console.log(response.data.data);
                window.location.href = `/post/${response.data.data.category}/${response.data.data.postId}`;
            });
        }catch(error){
            console.error("게시물 데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    }


    const deleteUser = async() =>{
        try{
        axios({
            headers: {
                "Content-Type": "application/json",
                'JWT': localStorage.getItem("JWT"),
            },
            method : "DELETE",
            url : "/user",
        }).then((response) =>{
            console.log(response.data.data);
            localStorage.removeItem("JWT");
            window.location.href = `/`;
        })
    }catch(error){
        console.log("유저 삭제 중 에러 발생  : ", error);
    }
}


    return(
        <div className="sidebar_wrapper">
            <div className="sidebar">
                <ul className="sidebar_ul">
                <li className="sidebar_li">
                    <button type= "button"
                            onClick={myPost}>
                        <h3>게시글</h3>
                    </button>
                </li>
                <li className="sidebar_li">
                    <Link to="/user/comments" className="sidebar_li_link">
                        <h3>댓글</h3>
                    </Link>
                </li>
                    <li className="sidebar_li">
                        <Link to="/user/posts/likes" className="sidebar_li_link">
                            <h3>좋아요</h3>
                        </Link>
                    </li>
                <li className="sidebar_li">
                    <button type= "button"
                            onClick={deleteUser}>
                        <h3>회원 탈퇴</h3>
                    </button>
                </li>
                </ul>
            </div>
        </div>
    )
}

export default Sidebar;