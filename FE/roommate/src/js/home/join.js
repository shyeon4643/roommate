import axios from "axios";
import React, { useState } from "react";
import "../../css/join.css";

function Join(){

    const[name, setName] = useState("");
    const[uid, setUid] = useState("");
    const[password, setPassword] = useState("");
    const[birth, setBirth] = useState("");
    const[email, setEmail] = useState("");
    const[mbti, setMbti] = useState("");
    const[nickname, setNickname] = useState("");
    const[phoneNum, setPhoneNum] = useState("");
    const[gender, setGender] = useState("");
    
const handleJoin=()=>{
    try{

        const data ={
            uid : uid,
            password : password,
            name:name,
            birth : birth,
            email : email,
            nickname : nickname,
            phoneNum : phoneNum,
            mbti : mbti,
            gender : gender
        }

    axios({
        method:"POST",
        url : "/join",
        data : data,
    }).then((response) =>{
        console.log(response.data);
        window.location.href = "/login";
    })
    }catch(error){
        console.log("회원 가입 중 오류 : ", error);
    }
    
}


    return (
        <div className="join_container">
            <div className="join_body">
            <h1 className="join_title">회원가입</h1>
            <form>
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
                                    <input type="password"
                                className="join_input"
                            placeholder="비밀번호"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">성별</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="성별"
                            value={gender}
                            onChange={(e) => setGender(e.target.value)}/>
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
                            <td className="join_field_name">MBTI</td>
                            <td>
                            <select
                                name="mbti"
                                className="join_input"
                                value={mbti}
                                onChange={(e) => setMbti(e.target.value)}
                            >
                                <option value="">MBTI를 선택하세요</option>
                                <option value="ISTJ" >ISTJ</option>
                                <option value="ISFJ" >ISFJ</option>
                                <option value="INFJ" >INFJ</option>
                                <option value="INTJ" >INTJ</option>
                                <option value="ISTP" >ISTP</option>
                                <option value="ISFP" >ISFP</option>
                                <option value="INFP" >INFP</option>
                                <option value="INTP" >INTP</option>
                                <option value="ESTP" >ESTP</option>
                                <option value="ESFP" >ESFP</option>
                                <option value="ENFP" >ENFP</option>
                                <option value="ENTP" >ENTP</option>
                                <option value="ESTJ" >ESTJ</option>
                                <option value="ESFJ" >ESFJ</option>
                                <option value="ENFJ" >ENFJ</option>
                                <option value="ENTJ" >ENTJ</option>
                            </select>
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
                    type="button"
                    onClick={handleJoin}
                >
                    회원가입
                </button>
                </form>
                </div>

            </div>
    )
}

export default Join;