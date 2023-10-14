import React from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "../../css/sidebar.css";

function Sidebar(){

    const deleteUser = async() =>{
        try{
        axios({
            headers: {
                "Content-Type": "application/json",
                'JWT': localStorage.getItem('JWT'),
            },
            method : "DELETE",
            url : "/user",
        }).then((response) =>{
            console.log(response.data.data);
            localStorage.removeItem('JWT');
            window.location.href = `/`;
        })
    }catch(error){
        console.log("유저 삭제 중 에러 발생  : ", error);
    }
}
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
                    <button type= "button"
                            onClick={deleteUser}>
                        <h3>회원 탈퇴</h3>
                    </button>
                </li>
                </ul>
            </div>
        </div>
    )
}

export default Sidebar;