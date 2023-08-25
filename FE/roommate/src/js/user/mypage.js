import React from "react";

function MyPage({imageUrl, name, nickname, email}){
    return(
        <div className="mypage_container">
            <div className="mypage_inform">
                <div className="profile_image">
                <img src={imageUrl} alt="프로필 이미지" className="mypage_image" />
                </div>
                <div className="profile_inform">
                <p>이름 : {name}</p>
                <p>닉네임 : {nickname}</p>
                <p>이메일 : {email}</p>
                </div>
                </div>
        </div>
    )

}

export default MyPage;