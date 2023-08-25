import React from "react";
import { Link } from "react-router-dom";
import "../../css/home.css";

function Home(){
    return (
        <div className="home_container">
            <div className="home">
            <div className="search">
                <h2>어느 지역을 찾으세요?</h2>
                <input type="text"
                id="search_input"/>
            </div>
            </div>
            <div className="post">
                <div className="posts">
                    <p>공지사항</p>
                    <Link to="/#" className="home_link"><p>+</p></Link>
                    </div>
                <div className="posts">
                    <p>최근 게시글</p>
                    <Link to="/#" className="home_link"><p>+</p></Link>
                </div>
            </div>
        </div>
    )

}

export default Home;