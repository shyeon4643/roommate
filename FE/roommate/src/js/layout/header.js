import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "../../css/header.css";

const  Header=()=>{
    const navigate = useNavigate();
    const logout = () => {
        // 로컬 스토리지에서 JWT 토큰 삭제
        localStorage.removeItem('JWT');
        // 로그아웃 후 리다이렉션할 페이지 설정 (예: 홈페이지로 이동)
        navigate('/');
    };
    const isUserLoggedIn = !!localStorage.getItem('JWT');
    return (
        <div className="header">
            <div id="logo">
                <Link to="/" className="header_link"><h1>Roomie</h1></Link>
            </div>
            <div id="header_header">
                <ul id="header_header_list">
                    <li>
                        <Link to="/charter/posts" className="header_link"><h4>전세</h4></Link>
                    </li>
                    <li>
                        <Link to="/monthly/posts" className="header_link"><h4>월세</h4></Link>
                    </li>
                    {isUserLoggedIn ? (
                        // JWT 토큰이 있을 때
                        <>
                            <li>
                                <Link to="/mypage" className="header_link"><h4>마이페이지</h4></Link>
                            </li>
                            <li>
                            <button onClick={logout} className="header_link" id="haeder_logout"><h4>로그아웃</h4></button>
                            </li>
                        </>
                    ) : (
                        // JWT 토큰이 없을 때
                        <>
                            <li>
                                <Link to="/login" className="header_link"><h4>로그인</h4></Link>
                            </li>
                            <li>
                                <Link to="/join" className="header_link"><h4>회원가입</h4></Link>
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </div>
    )
}

export default Header;