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
                    className="login-submit-button"
                    type="button"
                    onClick={handleLogin}
                >
                  로그인
                </button>
                </form>
            </div>
        </div>
    )
}

export default Login;