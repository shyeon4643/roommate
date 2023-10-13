import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../../css/mypage.css";
import Sidebar from "../layout/sidebar";
import "../layout/sidebar.js";
import DetailRoommate from "./detailRoommate";

function MyPage(){

    const [myPage, setMyPage] = useState("");

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

    return(
        <div className="mypage_wrapper">
            <Sidebar />
        <div className="mypage_container">
            <div className="mypage_continer_name">
                    <h1 className="mypage_name">회원정보</h1>
                </div>
            <div className="mypage_inform">
                <div className="profile_image">
                <img src={myPage.imageUrl} alt="프로필 이미지" className="mypage_image" />
                </div>
                <div className="profile_inform">
                <p className="profile_field">이름 : {myPage.name}</p>
                <p className="profile_field">닉네임 : {myPage.nickname}</p>
                <p className="profile_field">이메일 : {myPage.email}</p>
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
                        <td className="mypage_roommateInfo_name">반려동물</td>
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
                        <td className="mypage_roommateInfo_name">원하는 룸메이트 상세설명</td>
                        <td className="detailRoommate_field">
                            {myPage.wishRoommate}
                        </td>
                    </tr>
                    </table>
                    </div>
                    <Link className="detailRoommateInfo" to={{
                        pathname: `/updateDetailRoommate`,
                        state: myPage,
                    }}> 내가 원하는 룸메이트 정보 수정하러 가기
                    </Link>
                </div>
        </div>
        </div>
    )

}

export default MyPage;