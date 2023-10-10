import axios from "axios";
import React, { useState } from "react";
import "../../css/login.css";


function Login(){

    const formData = new FormData();
    const [uid, setUid] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin =()=>{
        try{
            formData.append("uid", uid);
            formData.append("password", password);
            axios({
                method : "POST",
                url : "/login",
                data:formData
            }).then((response) => {
                const JWT = response;
                localStorage.setItem("JWT", JWT);
            })
        }catch(error){
            console.log("로그인 중 에러 발생 : ", error);
        }
    }


   

    return(
        <div className="login_container">
            <div className="login_body">
                <h1 id="login_title">로그인</h1>
                <form onSubmit={handleLogin}>
                <input type="text"
                className="login_input"
                placeholder="ID"
                value={uid}
                onChange={(e) => setUid(e.target.value)}/>
                <input type="text"
                className="login_input"
                placeholder="PASSWORD"
                value={password}
                onChange={(e) => setPassword(e.target.value)}/>
                <button
                    className="login-submit-button"
                    type="submit"
                >
                  로그인
                </button>
                </form>
            </div>
        </div>
    )
}

export default Login;