import axios from "axios";
import React, { useState } from "react";
import "../../css/join.css";

function Join(){

    const formData = new FormData();
    const[name, setName] = useState("");
    const[uid, setUid] = useState("");
    const[password, setPassword] = useState("");
    const[birth, setBirth] = useState("");
    const[email, setEmail] = useState("");
    const[nickname, setNickname] = useState("");
    const[phoneNum, setPhoneNum] = useState("");
    
const handleJoin=()=>{
    try{
    formData.append("name", name);
    formData.append("uid",uid);
    formData.append("password",password);
    formData.append("birth", birth);
    formData.append("email",email);
    formData.append("nickname",nickname);
    formData.append("phoneNum",phoneNum);

    axios({
        headers :{
            "Content-Type": `application/json`,
        },
        method:"POST",
        url : "/join",
        data : formData,
    }).then((response) =>{
        window.location.href = "/login"
    })
    }catch(error){
        console.log("회원 가입 중 오류 : ", error);
    }
    
}


    return (
        <div className="join_container">
            <div className="join_body">
            <h1 className="join_title">회원가입</h1>
            <form onSubmit={handleJoin}>
                    <table className="join_table">
                        <tbody>
                        <tr>
                                <td className="join_field_name">이름</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="이름"
                            value={name}
                            onChange={(e) => setName(e.target.value)}/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">아이디</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="아이디"
                            value={uid}
                            onChange={(e) => setUid(e.target.value)}/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">비밀번호</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="비밀번호"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">생일</td>
                                <td>
                                    <input type="date"
                                className="join_input"
                            placeholder="생일"
                            value={birth}
                            onChange={(e) => setBirth(e.target.value)}/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">이메일</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="이메일"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">닉네임</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="닉네임"
                            value={nickname}
                            onChange={(e) => setNickname(e.target.value)}/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">전화번호</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="전화번호"
                            value={phoneNum}
                            onChange={(e) => setPhoneNum(e.target.value)}/>
                            </td>
                            </tr>
                            </tbody>
                    </table>
                    <button
                    className="join-submit-button"
                    type="submit"
                >
                    회원가입
                </button>
                </form>
                </div>

            </div>
    )
}

export default Join;