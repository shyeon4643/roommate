import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../../css/mypage.css";
import Sidebar from "../layout/sidebar";
import "../layout/sidebar.js";
import DetailRoommate from "./detailRoommate";
import myImage from '../../photo.png';

function MyPage(){

    const [myPage, setMyPage] = useState("");
    const navigate = useNavigate();

    useEffect(()=>{
        try{
            axios({
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
                method: "GET",
                url: `/mypage`,
            }).then((response) =>{
                setMyPage(response.data.data);
            });
        }catch(error){
            console.error("데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    },[]);

    const updateDetailRoommate = () =>{
        navigate("/updateDetailRoommate", {
            state: {
               category : myPage.category,
               area : myPage.area,
               lifeCycle : myPage.lifeCycle,
               pet : myPage.pet,
               smoking : myPage.smoking,
               gender : myPage.gender,
               wishRoommate : myPage.wishRoommate,
               fee : myPage.fee,
               message: "수정 완료",
            },
        });
    }
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
                window.location.href = `/posts/${response.data.data.category}/${response.data.data.postId}`;
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


const likedPosts = () =>{
    window.location.href="/user/posts/likes";
}

const myComments = () =>{
    window.location.href="/user/comments";
}




    return(
        <div className="mypage_wrapper">
        <div className="mypage_container">
            <div className="mypage_profile">
                <div className="mypage_img">
                <img src={myImage} alt="My Image" />
                </div>
                <div className="mypage_user_inform">
                <p className="profile_field">이름 : {myPage.name}</p>
                <p className="profile_field">닉네임 : {myPage.nickname}</p>
                <p className="profile_field">이메일 : {myPage.email}</p>
                </div>
            </div>
            <div className="mypage_inform">
            <ul className="mypage_ul">
                <li className="mypage_li">
                <button
                        className="mypage_button"
                    onClick={updateDetailRoommate}
                    type="button">
                        <p className="mypage_button_title">회원 정보 수정</p>
                        <p className="mypage_button_description">이름, 이메일, 성별 등을 수정합니다.</p>
                    </button>
                    </li>
                    <li className="mypage_li">
                <button
                        className="mypage_button"
                    onClick={updateDetailRoommate}
                    type="button">
                       <p className="mypage_button_title">룸메이트 정보 수정</p>
                       <p className="mypage_button_description"> 내가 원하는 룸메이트에 대한 정보를 수정합니다.</p>
                    </button>
                    </li>
                    <li className="mypage_li">
                    <button type= "button"
                    className="mypage_button"
                            onClick={myPost}>
                        <p className="mypage_button_title">게시글</p>
                       <p className="mypage_button_description">내가 쓴 게시글을 보여줍니다.</p>
                    </button>
                </li>
                <li className="mypage_li">
                <button type= "button"
                className="mypage_button"
                            onClick={myComments}>
                        <p className="mypage_button_title">댓글</p>
                       <p className="mypage_button_description">내가 쓴 댓글을 보여줍니다.</p>
                    </button>
                </li>
                    <li className="mypage_li">
                    <button type= "button"
                    className="mypage_button"
                            onClick={likedPosts}>
                        <p className="mypage_button_title">좋아요</p>
                       <p className="mypage_button_description">내가 좋아요한 게시글을 보여줍니다.</p>
                    </button>
                    </li>
                <li className="mypage_li">
                    <button type= "button"
                    className="mypage_button"
                            onClick={deleteUser}>
                        <p className="mypage_button_title">회원탈퇴</p>
                    </button>
                </li>
                </ul>
            </div>
        </div>
        </div>
    )

}

export default MyPage;