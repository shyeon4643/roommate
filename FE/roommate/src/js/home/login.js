import React from "react";
import "../../css/login.css";

const handleLogin =()=>{

}

function Login(){
    return(
        <div className="login_container">
            <div className="login_body">
                <h1 id="login_title">로그인</h1>
                <input type="text"
                className="login_input"
                placeholder="ID"/>
                <input type="text"
                className="login_input"
                placeholder="PASSWORD"/>
                <button
                    className="login-submit-button"
                    onClick={handleLogin}
                >
                  로그인
                </button>
            </div>
        </div>
    )
}

export default Login;