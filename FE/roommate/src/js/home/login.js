import axios from "axios";
import React, { useState } from "react";
import "../../css/login.css";


function Login(){
    const [uid, setUid] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin =()=>{
        try{
            const data ={
                uid : uid,
                password : password
            }
            axios({
                method : "POST",
                url : "/login",
                data:data,
            }).then((response) => {
                console.log(response.data.data);
                localStorage.setItem("JWT", response.data.data.token);
                if(response.data.data.info===false) {
                    window.location.href = "/writeDetailRoommate";
                }else {
                    window.location.href = "/";
                }
            })
        }catch(error){
            console.log("로그인 중 에러 발생 : ", error);
        }
    }

    const handleGetKakaoCode = () =>{
        const restApiKey = '10965eecbd4e1c23538795681c05f934';
        const redirectUri = 'http://localhost:3000/oauth/kakao/redirect';

        
        const data ={
            uid : uid,
            password : password
        }
        const queryParams = new URLSearchParams(data);
        const kakaoUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUri}&response_type=code&${queryParams.toString()}`
        window.location.href = kakaoUrl;
    }



   

    return(
        <div className="login_container">
            <div className="login_body">
                <h1 id="login_title">로그인</h1>
                <form>
                <input type="text"
                className="login_input"
                placeholder="ID"
                value={uid}
                onChange={(e) => setUid(e.target.value)}/>
                <input type="password"
                className="login_input"
                placeholder="PASSWORD"
                value={password}
                onChange={(e) => setPassword(e.target.value)}/>
                <button
                    className="login_submit_button"
                    type="button"
                    onClick={handleLogin}
                >
                  로그인
                </button>
                <button
                    className="kakao_login_submit_button"
                    type="button"
                    onClick={handleGetKakaoCode}
                >
                  카카오 로그인 하러 가기
                </button>
                </form>
            </div>
        </div>
    )
}

export default Login;