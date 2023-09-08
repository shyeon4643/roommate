import React from "react";
import { Link } from "react-router-dom";
import "../../css/sidebar.css";

function Sidebar(){
    return(
        <div className="sidebar_wrapper">
            <div className="sidebar">
                <ul className="sidebar_ul">
                <li className="sidebar_li">
                    <Link to="/user/posts" className="sidebar_li_link">
                        <h3>내가 쓴 글</h3>
                    </Link>
                </li>
                <li className="sidebar_li">
                    <Link to="/user/comments" className="sidebar_li_link">
                        <h3>내가 쓴 댓글</h3>
                    </Link>
                </li>
                <li className="sidebar_li">
                    <Link to="/delete/user" className="sidebar_li_link">
                        <h3>회원 탈퇴</h3>
                    </Link>
                </li>
                </ul>
            </div>
        </div>
    )
}

export default Sidebar;