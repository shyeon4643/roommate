import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../../css/mypage.css";
import Sidebar from "../layout/sidebar";
import "../layout/sidebar.js";
import DetailRoommate from "./detailRoommate";
import myImage from '../../photo.jpeg';

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

    return(
        <div className="mypage_wrapper">
            <Sidebar />
        <div className="mypage_container">
            <div className="mypage_continer_name">
                    <h1 className="mypage_name">회원정보</h1>
                </div>
            <div className="mypage_inform">
                <div className="profile_image">
                <img src={myImage} alt="My Image" />
                </div>
                <div className="profile_inform">
                <p className="profile_field">이름 : {myPage.name}</p>
                <p className="profile_field">닉네임 : {myPage.nickname}</p>
                <p className="profile_field">이메일 : {myPage.email}</p>
                <p className="profile_field">이런 사람을 원해요</p>
                <p className="profile_field" id="wish_roommate_field">{myPage.wishRoommate}</p>
                </div>
                </div>
                <div className="mypage_roommateInfo">
                    <div>
                        <table className="mypage_roommateInfo_table">
                    <tr>
                        <td className="mypage_roommateInfo_name">생활패턴</td>
                        <td className="detailRoommate_field">
                            {myPage.lifeCycle}
                        </td>
                        <td className="mypage_roommateInfo_name"> 반려동물</td>
                        <td className="detailRoommate_field">
                            {myPage.pet}
                        </td>
                        </tr>
                        <tr>
                        <td className="mypage_roommateInfo_name">담배</td>
                        <td className="detailRoommate_field">
                            {myPage.smoking}
                        </td>
                        <td className="mypage_roommateInfo_name">성별</td>
                        <td className="detailRoommate_field">
                            {myPage.gender}
                        </td>
                        </tr>
                        <tr>
                        <td className="mypage_roommateInfo_name">금액</td>
                        <td className="detailRoommate_field">
                            {myPage.fee}
                        </td>
                    
                    </tr>
                    </table>
                    </div>
                    <button
                        className="detailRoommateInfo"
                    onClick={updateDetailRoommate}
                    type="button">
                        내가 원하는 룸메이트 정보 수정하러 가기
                    </button>
                </div>
        </div>
        </div>
    )

}

export default MyPage;