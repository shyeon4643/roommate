import React from "react";
import { Link } from "react-router-dom";
import "../../css/header.css";

const  Header=()=>{
    return(
        <div className="header">
            <div id="logo">
            <Link to="/" className="header_link"><h1>
                Together
                </h1></Link>
            </div>
            <div id="header_header">
                <ul id="header_header_list">
                    <li>
                    <Link to="/monthly/posts" className="header_link"><h4>전세</h4></Link>
                    </li>
                    <li>
                    <Link to="/charter/posts" className="header_link"><h4>월세</h4></Link>
                    </li>
                    <li>
                    <Link to="/login" className="header_link"><h4>로그인</h4></Link>
                    </li>
                    <li>
                    <Link to="/join" className="header_link"><h4>회원가입</h4></Link>
                    </li>
                </ul>
            </div>
        </div>
    )
}
export default Header;