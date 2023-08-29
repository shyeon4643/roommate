import React from "react";
import { Link } from "react-router-dom";
import "../../css/mypage.css";

function MyPage({imageUrl, name, nickname, email, lifeCycle, pet, smoking, gender, wishRoommate, fee}){
    return(
        <div className="mypage_container">
            <div className="mypage_continer_name">
                    <h1 className="mypage_name">회원정보</h1>
                </div>
            <div className="mypage_inform">
                <div className="profile_image">
                <img src={imageUrl} alt="프로필 이미지" className="mypage_image" />
                </div>
                <div className="profile_inform">
                <p className="profile_field">이름 : {name}</p>
                <p className="profile_field">닉네임 : {nickname}</p>
                <p className="profile_field">이메일 : {email}</p>
                </div>
                </div>
                <div className="mypage_roommateInfo">
                    <div>
                        <table className="mypage_roommateInfo_table">
                    <tr>
                        <td className="mypage_roommateInfo_name">생활패턴</td>
                        <td className="detailRoommate_field">
                            {lifeCycle}
                        </td>
                        <td className="mypage_roommateInfo_name">반려동물</td>
                        <td className="detailRoommate_field">
                            {pet}
                        </td>
                        </tr>
                        <tr>
                        <td className="mypage_roommateInfo_name">담배</td>
                        <td className="detailRoommate_field">
                            {smoking}
                        </td>
                        <td className="mypage_roommateInfo_name">성별</td>
                        <td className="detailRoommate_field">
                            {gender}
                        </td>
                        </tr>
                        <tr>
                        <td className="mypage_roommateInfo_name">금액</td>
                        <td className="detailRoommate_field">
                            {fee}
                        </td>
                        <td className="mypage_roommateInfo_name">원하는 룸메이트 상세설명</td>
                        <td className="detailRoommate_field">
                            {wishRoommate}
                        </td>
                    </tr>
                    </table>
                    </div>
                <Link to="/detailRoommate" className="detailRoommateInfo">내가 원하는 룸메이트 정보 수정하기</Link>
                </div>
        </div>
    )

}

export default MyPage;